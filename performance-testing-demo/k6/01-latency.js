/**
 * ══════════════════════════════════════════════════════════════
 *  k6 — PRUEBA DE LATENCIA
 * ══════════════════════════════════════════════════════════════
 *
 * CONCEPTO:
 *   Latencia = tiempo que tarda una sola solicitud en completarse
 *   (desde que el cliente la envía hasta que recibe la última byte).
 *
 *   Métricas clave:
 *     • http_req_duration  → tiempo total de la petición
 *     • p(50)  Percentil 50 (mediana): la mitad de las peticiones son más rápidas
 *     • p(90)  Percentil 90: el 10% más lento tarda esto o más
 *     • p(95)  Percentil 95: referencia estándar de SLA
 *     • p(99)  Percentil 99: "cola larga" (outliers)
 *
 * ESTRATEGIA:
 *   - Pocos usuarios virtuales (VUs) para no saturar el servidor.
 *   - Se prueban 3 endpoints con diferente latencia esperada.
 *   - Las thresholds definen los criterios de éxito/fallo.
 *
 * EJECUCIÓN:
 *   k6 run k6/01-latency.js
 *   k6 run --out json=results/latency.json k6/01-latency.js
 */

import http    from 'k6/http';
import { check, sleep } from 'k6';
import { Trend, Rate } from 'k6/metrics';

// ─── Métricas personalizadas ──────────────────────────────────────────────────
const fastLatency   = new Trend('fast_latency_ms',   true);
const mediumLatency = new Trend('medium_latency_ms', true);
const slowLatency   = new Trend('slow_latency_ms',   true);
const errorRate     = new Rate('error_rate');

// ─── Configuración del escenario ──────────────────────────────────────────────
export const options = {
  // Pocas VUs: el objetivo NO es carga masiva sino medición de tiempo de respuesta
  vus:      5,
  duration: '30s',

  thresholds: {
    // El endpoint /fast debe responder en menos de 50ms el 95% del tiempo
    'fast_latency_ms{p(95)}': ['p(95)<50'],

    // El endpoint /medium debe responder en menos de 200ms el 95% del tiempo
    'medium_latency_ms{p(95)}': ['p(95)<200'],

    // El endpoint /slow debe responder en menos de 800ms el 95% del tiempo
    'slow_latency_ms{p(95)}': ['p(95)<800'],

    // Métrica global: p(95) de todas las peticiones bajo 800ms
    http_req_duration: ['p(95)<800'],

    // Menos del 1% de errores HTTP
    error_rate: ['rate<0.01'],
  },
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:3000';

// ─── Función principal (se ejecuta por cada VU en cada iteración) ─────────────
export default function () {

  // ── 1. Endpoint rápido ────────────────────────────────────────────────────
  let res = http.get(`${BASE_URL}/fast`);
  fastLatency.add(res.timings.duration);
  errorRate.add(res.status !== 200);
  check(res, {
    '[/fast] status 200':        (r) => r.status === 200,
    '[/fast] latencia < 50ms':   (r) => r.timings.duration < 50,
  });

  sleep(0.5); // pausa entre peticiones (simula usuario real)

  // ── 2. Endpoint con latencia moderada ─────────────────────────────────────
  res = http.get(`${BASE_URL}/medium`);
  mediumLatency.add(res.timings.duration);
  errorRate.add(res.status !== 200);
  check(res, {
    '[/medium] status 200':         (r) => r.status === 200,
    '[/medium] latencia < 200ms':   (r) => r.timings.duration < 200,
  });

  sleep(0.5);

  // ── 3. Endpoint con latencia alta ─────────────────────────────────────────
  res = http.get(`${BASE_URL}/slow`);
  slowLatency.add(res.timings.duration);
  errorRate.add(res.status !== 200);
  check(res, {
    '[/slow] status 200':          (r) => r.status === 200,
    '[/slow] latencia < 800ms':    (r) => r.timings.duration < 800,
  });

  sleep(1);
}

// ─── Resumen final ────────────────────────────────────────────────────────────
export function handleSummary(data) {
  const dur = data.metrics.http_req_duration;
  if (!dur) return {};

  console.log('\n╔══════════════════════════════════════╗');
  console.log('║      RESUMEN DE LATENCIA             ║');
  console.log('╠══════════════════════════════════════╣');
  console.log(`║  P50  (mediana) : ${dur.values['p(50)']?.toFixed(2).padStart(8)} ms      ║`);
  console.log(`║  P90            : ${dur.values['p(90)']?.toFixed(2).padStart(8)} ms      ║`);
  console.log(`║  P95            : ${dur.values['p(95)']?.toFixed(2).padStart(8)} ms      ║`);
  console.log(`║  P99  (cola)    : ${dur.values['p(99)']?.toFixed(2).padStart(8)} ms      ║`);
  console.log(`║  Máx            : ${dur.values['max']?.toFixed(2).padStart(8)} ms      ║`);
  console.log('╚══════════════════════════════════════╝\n');

  return {};
}
