# Performance Testing Demo
## Latencia · Throughput · Saturación con k6 y Artillery

---

## Conceptos fundamentales

### 1. Latencia

> **Latencia** es el tiempo que tarda una sola petición en completarse,
> desde que el cliente la envía hasta que recibe el último byte de respuesta.

Se mide en **percentiles**, no en promedio:

| Percentil | Significado |
|-----------|-------------|
| **P50** (mediana) | La mitad de las peticiones son más rápidas que este valor |
| **P90** | El 10% más lento tarda esto o más |
| **P95** | Referencia estándar en SLAs y acuerdos de nivel de servicio |
| **P99** | La "cola larga": outliers que afectan a 1 de cada 100 usuarios |

**¿Por qué no usar el promedio?** Un promedio de 100ms puede esconder que
el 1% de usuarios espera 10 segundos. El P99 lo revela.

```
Ejemplo:
  P50 = 80ms  ← La mayoría de usuarios ve esto
  P95 = 200ms ← Tu SLA debería basarse en este
  P99 = 900ms ← Usuarios con mala suerte
  Max = 4500ms← Un outlier que contaminaría el promedio
```

---

### 2. Throughput

> **Throughput** es la cantidad de trabajo completado por unidad de tiempo.
> En servicios web: **Requests Per Second (RPS)**.

**Ley de Little** (base de la teoría de colas):

$$N = \lambda \times W$$

Donde:
- $N$ = usuarios concurrentes (VUs)
- $\lambda$ = throughput (req/s)
- $W$ = latencia promedio (segundos)

**Ejemplo práctico:**
```
50 VUs, latencia promedio 50ms
→ Throughput = 50 / 0.05 = 1000 req/s (teórico)
```

**Throughput ≠ Latencia** — un sistema puede tener alto throughput pero alta latencia
(procesando muchas peticiones pero cada una tarda mucho).

---

### 3. Saturación

> **Saturación** es el punto en que un recurso del sistema (CPU, memoria,
> conexiones de BD, hilos de servidor) alcanza el 100% de su capacidad.

**Señales de saturación:**

| Indicador | Qué medir | Señal de alarma |
|-----------|-----------|-----------------|
| CPU | `top`, `/cpu` endpoint latency | > 80% sostenido |
| Colas | `http_req_waiting` en k6 | Sube mientras throughput se estanca |
| Conexiones | `http_req_blocked` en k6 | Connection pool agotado |
| Errores | `error_rate`, códigos 500/503 | > 1% en operación normal |
| Latencia cola | P99 >> P50 | Distribución con "cola larga" |

**Tipos de prueba para detectar saturación:**
- **Stress test**: aumenta la carga progresivamente hasta encontrar el límite
- **Spike test**: carga masiva repentina para probar resiliencia
- **Soak test**: carga sostenida durante horas para detectar memory leaks

---

## Estructura del proyecto

```
performance-testing-demo/
├── server/
│   ├── package.json
│   └── server.js          ← API Express con endpoints de diferente latencia
├── k6/
│   ├── 01-latency.js      ← Prueba de latencia (k6)
│   ├── 02-throughput.js   ← Prueba de throughput con rampa (k6)
│   └── 03-saturation.js   ← Stress + Spike test (k6)
├── artillery/
│   ├── 01-latency.yml     ← Prueba de latencia (Artillery)
│   ├── 02-throughput.yml  ← Prueba de throughput (Artillery)
│   └── 03-saturation.yml  ← Stress + Spike test (Artillery)
└── package.json           ← Scripts de conveniencia
```

---

## Endpoints del servidor de prueba

| Endpoint | Latencia esperada | Propósito |
|----------|-------------------|-----------|
| `GET /health` | ~1ms | Estado del servidor (RPS acumulado) |
| `GET /fast` | < 5ms | Throughput máximo puro |
| `GET /medium` | 50–150ms | I/O moderada (caché miss, DB ligera) |
| `GET /slow` | 300–700ms | Consulta pesada / servicio externo |
| `GET /cpu` | variable | **Saturación de CPU** (10k SHA-256) |
| `GET /unstable` | 20–100ms | 20% de errores 500 (tasa de errores) |
| `GET /echo?delay=<ms>` | configurable | Experimentos ad-hoc |

---

## Instalación y ejecución

### Prerrequisitos

```bash
# Node.js (para el servidor)
node --version  # >= 18

# k6 — macOS
brew install k6

# k6 — Linux
sudo gpg -k
sudo gpg --no-default-keyring \
  --keyring /usr/share/keyrings/k6-archive-keyring.gpg \
  --keyserver hkp://keyserver.ubuntu.com:80 \
  --recv-keys C5AD17C747E3415A3642D57D77C6C491D6AC1D69
echo "deb [signed-by=/usr/share/keyrings/k6-archive-keyring.gpg] https://dl.k6.io/deb stable main" | \
  sudo tee /etc/apt/sources.list.d/k6.list
sudo apt-get update && sudo apt-get install k6

# Artillery
npm install -g artillery
```

### Paso 1: Arrancar el servidor

```bash
cd performance-testing-demo/server
npm install
npm start
# → Servidor corriendo en http://localhost:3000
```

Verificar que está activo:
```bash
curl http://localhost:3000/health
```

---

### Paso 2: Ejecutar pruebas k6

#### Latencia
```bash
k6 run k6/01-latency.js

# Con URL personalizada:
k6 run -e BASE_URL=http://mi-servidor.com k6/01-latency.js

# Con salida JSON para análisis posterior:
k6 run --out json=results/latency.json k6/01-latency.js
```

**Qué observar:**
- `http_req_duration` → P50, P90, P95, P99
- `fast_latency_ms`, `medium_latency_ms`, `slow_latency_ms` (métricas custom)
- Checks que fallan (si la latencia supera los umbrales)

---

#### Throughput
```bash
k6 run k6/02-throughput.js
```

**Qué observar:**
- `http_reqs` → la tasa (req/s) — línea clave del reporte
- Cómo la latencia P95 varía a medida que suben los VUs
- Si `http_reqs` deja de crecer aunque suban los VUs → throughput máximo alcanzado

---

#### Saturación
```bash
k6 run k6/03-saturation.js
```

**Qué observar:**
- `http_req_blocked` → sube cuando el connection pool se agota
- `http_req_waiting` → sube cuando la cola del servidor está llena
- `error_rate` → crece al llegar al punto de quiebre
- P99 se dispara mientras P50 permanece bajo → cola larga

---

### Paso 3: Ejecutar pruebas Artillery

#### Latencia
```bash
artillery run artillery/01-latency.yml

# Con reporte HTML:
artillery run artillery/01-latency.yml --output results/latency-artillery.json
artillery report results/latency-artillery.json
```

#### Throughput
```bash
artillery run artillery/02-throughput.yml
```

#### Saturación
```bash
artillery run artillery/03-saturation.yml
```

**Qué observar en Artillery:**
- `http.response_time` → p95, p99
- `http.request_rate` → req/s reales
- `http.codes.2xx/5xx` → distribución de respuestas
- `errors` → errores de conexión/timeout

---

## k6 vs Artillery — Comparativa rápida

| Característica | k6 | Artillery |
|----------------|-----|-----------|
| **Lenguaje** | JavaScript (ES6+) | YAML + JS opcional |
| **Instalación** | Binario único | npm global |
| **Métricas custom** | `Trend`, `Rate`, `Counter`, `Gauge` | Plugins |
| **Reporte HTML** | `k6 run --out web-dashboard` | `artillery report` |
| **CI/CD** | Excelente (binario) | Bueno (npm) |
| **Curva de aprendizaje** | Media | Baja (YAML) |
| **Escenarios complejos** | Muy flexible (JS) | Moderado |
| **Protocolo** | HTTP, WebSocket, gRPC | HTTP, WebSocket |

---

## Interpretación de resultados

### Semáforo de rendimiento

```
✅ VERDE   — P95 < umbral, error_rate < 1%, throughput estable
⚠️  AMARILLO — P95 cerca del umbral, error_rate 1-5%
❌ ROJO    — P95 supera umbral, error_rate > 5%, throughput se estanca
```

### Ejemplo de salida k6 (latency test)

```
✓ [/fast] status 200
✓ [/fast] latencia < 50ms
✓ [/medium] status 200
✓ [/medium] latencia < 200ms
✓ [/slow] status 200

http_req_duration............: avg=210.3ms  p(90)=512ms  p(95)=634ms  p(99)=712ms
fast_latency_ms..............: avg=2.1ms    p(90)=4ms    p(95)=6ms    p(99)=12ms
medium_latency_ms............: avg=98.5ms   p(90)=142ms  p(95)=149ms  p(99)=155ms
slow_latency_ms..............: avg=498.2ms  p(90)=672ms  p(95)=694ms  p(99)=710ms
```

---

## Referencias

- [k6 docs — Metrics](https://k6.io/docs/using-k6/metrics/)
- [Artillery docs — Test scripts](https://www.artillery.io/docs/reference/test-script)
- [Google SRE Book — Latency](https://sre.google/sre-book/monitoring-distributed-systems/)
- [Ley de Little](https://en.wikipedia.org/wiki/Little%27s_law)
