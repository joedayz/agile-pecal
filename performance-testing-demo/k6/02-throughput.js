/**
 * ══════════════════════════════════════════════════════════════
 *  k6 — PRUEBA DE THROUGHPUT
 * ══════════════════════════════════════════════════════════════
 *
 * CONCEPTO:
 *   Throughput = cantidad de trabajo completado por unidad de tiempo.
 *   En servicios web se mide como:
 *     • RPS (Requests Per Second) — peticiones por segundo
 *     • También puede ser transacciones/s, MB/s, etc.
 *
 *   Throughput ≠ Latencia:
 *     Un sistema puede procesar 1000 req/s pero con 200ms de latencia cada una.
 *     Ambas métricas son complementarias.
 *
 *   Ley de Little (Teoría de colas):
 *     Throughput = Usuarios concurrentes / Latencia promedio
 *     N = λ × W   →   λ (throughput) = N / W
 *
 * ESTRATEGIA:
 *   Se usa un escenario de "ramp-up" (rampa de carga):
 *     1. Inicio suave para calentar el servidor
 *     2. Sube a carga objetivo
 *     3. Mantiene la carga para medir throughput estable
 *     4. Baja de forma limpia
 *
 * EJECUCIÓN:
 *   k6 run k6/02-throughput.js
 *   k6 run --out json=results/throughput.json k6/02-throughput.js
 */

import http from 'k6/http';
import { check, sleep } from 'k6';
import { Rate, Counter, Trend } from 'k6/metrics';

// ─── Métricas personalizadas ──────────────────────────────────────────────────
const successRate    = new Rate('success_rate');
const requestsTotal  = new Counter('requests_total');
const responseTrend  = new Trend('response_time_ms', true);

// ─── Escenario de ramp-up ─────────────────────────────────────────────────────
export const options = {
  stages: [
    { duration: '10s', target: 5  },   // Calentamiento: 5 VUs
    { duration: '20s', target: 20 },   // Sube a 20 VUs
    { duration: '30s', target: 50 },   // Carga objetivo: 50 VUs
    { duration: '20s', target: 50 },   // Mantiene carga estable
    { duration: '10s', target: 10 },   // Enfriamiento
    { duration: '10s', target: 0  },   // Baja a 0
  ],

  thresholds: {
    // El servidor debe procesar al menos 50 req/s bajo carga máxima
    http_reqs: ['rate>50'],

    // El 95% de respuestas deben ser < 300ms
    http_req_duration: ['p(95)<300'],

    // Al menos 99% de éxitos
    success_rate: ['rate>0.99'],
  },
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:3000';

// ─── Función principal ────────────────────────────────────────────────────────
export default function () {
  // Usamos /fast para que el cuello de botella sea la concurrencia, no la latencia
  const res = http.get(`${BASE_URL}/fast`);

  requestsTotal.add(1);
  responseTrend.add(res.timings.duration);

  const ok = check(res, {
    'status 200':       (r) => r.status === 200,
    'cuerpo válido':    (r) => r.body && r.body.length > 0,
    'latencia < 300ms': (r) => r.timings.duration < 300,
  });
  successRate.add(ok);

  // Sin sleep entre peticiones para maximizar throughput medible
  // (en escenarios reales se añade sleep para simular tiempo de "think time")
}

// ─── Resumen final ────────────────────────────────────────────────────────────
export function handleSummary(data) {
  const reqs = data.metrics.http_reqs;
  const dur  = data.metrics.http_req_duration;
  if (!reqs || !dur) return {};

  const rps      = reqs.values.rate.toFixed(2);
  const totalReq = reqs.values.count;
  const p95      = dur.values['p(95)']?.toFixed(2);
  const avg      = dur.values['avg']?.toFixed(2);

  console.log('\n╔══════════════════════════════════════╗');
  console.log('║      RESUMEN DE THROUGHPUT           ║');
  console.log('╠══════════════════════════════════════╣');
  console.log(`║  RPS (req/s)    : ${rps.padStart(12)}       ║`);
  console.log(`║  Total peticio. : ${String(totalReq).padStart(12)}       ║`);
  console.log(`║  Latencia media : ${avg.padStart(10)} ms     ║`);
  console.log(`║  P95 latencia   : ${p95.padStart(10)} ms     ║`);
  console.log('╠══════════════════════════════════════╣');
  console.log('║  Ley de Little:                      ║');
  console.log('║  Throughput = VUs / Latencia media   ║');
  console.log('╚══════════════════════════════════════╝\n');

  return {};
}
