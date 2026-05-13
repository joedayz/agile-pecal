/**
 * ══════════════════════════════════════════════════════════════
 *  k6 — PRUEBA DE SATURACIÓN (Stress / Spike Test)
 * ══════════════════════════════════════════════════════════════
 *
 * CONCEPTO:
 *   Saturación = el punto en que un recurso del sistema está al 100%
 *   de su capacidad (CPU, memoria, hilos, conexiones de DB, etc.).
 *
 *   Cuando hay saturación:
 *     1. La latencia se dispara (el trabajo se encola)
 *     2. El throughput se estanca o cae (no puede procesar más)
 *     3. Los errores aumentan (timeouts, OOM, 503, etc.)
 *
 *   Señales de saturación:
 *     • http_req_blocked  sube (conexiones agotadas)
 *     • http_req_waiting  sube (cola en servidor)
 *     • Error rate sube de 0% a >1%
 *     • P99 latencia ≫ P50 (distribución con cola larga)
 *
 *   Fases de este test:
 *     1. Stress progresivo: sube carga continuamente hasta encontrar el límite
 *     2. Spike (pico): carga repentina masiva para probar la resiliencia
 *     3. Recuperación: la carga baja, el sistema debe recuperarse
 *
 * EJECUCIÓN:
 *   k6 run k6/03-saturation.js
 *   k6 run --out json=results/saturation.json k6/03-saturation.js
 */

import http    from 'k6/http';
import { check, sleep } from 'k6';
import { Rate, Trend } from 'k6/metrics';

// ─── Métricas personalizadas ──────────────────────────────────────────────────
const errorRate    = new Rate('error_rate');
const cpuLatency   = new Trend('cpu_latency_ms', true);
const spikeLatency = new Trend('spike_latency_ms', true);

// ─── Escenario de saturación ──────────────────────────────────────────────────
export const options = {
  scenarios: {
    // ── Escenario 1: Stress progresivo ───────────────────────────────────────
    // Incrementa VUs continuamente para encontrar el punto de quiebre
    stress_test: {
      executor:  'ramping-vus',
      startVUs:  0,
      stages: [
        { duration: '15s', target: 10  },  // normal
        { duration: '15s', target: 30  },  // aumento moderado
        { duration: '15s', target: 80  },  // carga alta
        { duration: '15s', target: 150 },  // posible saturación
        { duration: '10s', target: 0   },  // recuperación
      ],
      gracefulRampDown: '5s',
    },

    // ── Escenario 2: Spike (pico repentino) ──────────────────────────────────
    // Simula un evento viral o ataque: tráfico masivo de golpe
    spike_test: {
      executor:  'ramping-vus',
      startVUs:  0,
      startTime: '80s',  // comienza después del stress test
      stages: [
        { duration: '5s',  target: 200 },  // pico instantáneo
        { duration: '10s', target: 200 },  // mantiene el pico
        { duration: '5s',  target: 0   },  // caída brusca
        { duration: '10s', target: 20  },  // ¿el servidor se recupera?
        { duration: '10s', target: 0   },  // fin
      ],
      gracefulRampDown: '5s',
    },
  },

  thresholds: {
    // Durante saturación esperamos errores — el umbral es más permisivo
    error_rate:        ['rate<0.15'],   // hasta 15% de errores es aceptable en pico

    // Si p99 supera 3 segundos, el sistema está claramente saturado
    http_req_duration: ['p(99)<3000'],

    // http_req_blocked mide tiempo esperando conexión TCP libre
    // Si sube mucho, el connection pool está agotado
    http_req_blocked:  ['p(95)<500'],
  },
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:3000';

// ─── Función principal ────────────────────────────────────────────────────────
export default function () {
  const scenario = __ENV.K6_SCENARIO_NAME || 'unknown';

  if (scenario === 'spike_test') {
    // El spike golpea el endpoint /fast para maximizar la presión de concurrencia
    const res = http.get(`${BASE_URL}/fast`, { timeout: '5s' });
    spikeLatency.add(res.timings.duration);
    errorRate.add(res.status !== 200);
    check(res, {
      '[spike] status 2xx': (r) => r.status >= 200 && r.status < 300,
    });

  } else {
    // El stress test golpea /cpu para saturar el procesador
    const res = http.get(`${BASE_URL}/cpu`, { timeout: '10s' });
    cpuLatency.add(res.timings.duration);
    errorRate.add(res.status !== 200);

    const ok = check(res, {
      '[stress] status 200':       (r) => r.status === 200,
      '[stress] responde < 5s':    (r) => r.timings.duration < 5000,
    });

    // Observar también el estado de la cola (http_req_waiting = tiempo en cola)
    check(res, {
      '[stress] cola < 2s': (r) => r.timings.waiting < 2000,
    });
  }

  // Pequeña pausa para no abusar del event loop de Node.js en el test
  sleep(0.1);
}

// ─── Resumen final ────────────────────────────────────────────────────────────
export function handleSummary(data) {
  const dur     = data.metrics.http_req_duration;
  const blocked = data.metrics.http_req_blocked;
  const waiting = data.metrics.http_req_waiting;
  const errs    = data.metrics.error_rate;

  if (!dur) return {};

  const errorPct = ((errs?.values?.rate || 0) * 100).toFixed(1);

  console.log('\n╔══════════════════════════════════════════════╗');
  console.log('║      RESUMEN DE SATURACIÓN                   ║');
  console.log('╠══════════════════════════════════════════════╣');
  console.log(`║  Tasa de errores : ${errorPct.padStart(8)}%               ║`);
  console.log('╠──────────────────────────────────────────────╣');
  console.log('║  LATENCIA TOTAL (http_req_duration)          ║');
  console.log(`║    P50  : ${dur.values['p(50)']?.toFixed(0).padStart(8)} ms                    ║`);
  console.log(`║    P95  : ${dur.values['p(95)']?.toFixed(0).padStart(8)} ms                    ║`);
  console.log(`║    P99  : ${dur.values['p(99)']?.toFixed(0).padStart(8)} ms                    ║`);
  console.log(`║    Max  : ${dur.values['max']?.toFixed(0).padStart(8)} ms                    ║`);
  console.log('╠──────────────────────────────────────────────╣');
  console.log('║  COLA (http_req_waiting)                     ║');
  console.log(`║    P95  : ${(waiting?.values['p(95)'] || 0).toFixed(0).padStart(8)} ms                    ║`);
  console.log('╠──────────────────────────────────────────────╣');
  console.log('║  CONEXIONES BLOQUEADAS (http_req_blocked)    ║');
  console.log(`║    P95  : ${(blocked?.values['p(95)'] || 0).toFixed(0).padStart(8)} ms                    ║`);
  console.log('╠══════════════════════════════════════════════╣');
  console.log('║  Señales de saturación:                      ║');
  console.log('║  ↑ error_rate    → recursos agotados         ║');
  console.log('║  ↑ req_waiting   → cola del servidor llena   ║');
  console.log('║  ↑ req_blocked   → connection pool agotado   ║');
  console.log('║  P99 >> P50      → distribución con cola     ║');
  console.log('╚══════════════════════════════════════════════╝\n');

  return {};
}
