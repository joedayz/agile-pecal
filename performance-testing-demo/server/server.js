/**
 * Servidor de demostración para pruebas de rendimiento
 * Expone endpoints con diferentes comportamientos:
 *  - /fast      → respuesta inmediata (~1ms)
 *  - /medium    → latencia moderada (50-150ms simulada)
 *  - /slow      → latencia alta (300-700ms simulada)
 *  - /cpu       → trabajo de CPU intensivo (hash loop)
 *  - /unstable  → 20% de probabilidad de error 500
 *  - /health    → health check
 */

const express = require('express');
const crypto  = require('crypto');

const app  = express();
const PORT = process.env.PORT || 3000;

// ─── Utilidades ────────────────────────────────────────────────────────────────

/** Retorna una promesa que se resuelve tras `ms` milisegundos */
const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

/** Latencia aleatoria entre min y max ms */
const randomDelay = (min, max) => sleep(Math.floor(Math.random() * (max - min + 1)) + min);

// ─── Métricas en memoria ───────────────────────────────────────────────────────

let stats = { requests: 0, errors: 0, start: Date.now() };

app.use((req, res, next) => {
  stats.requests++;
  next();
});

// ─── Endpoints ─────────────────────────────────────────────────────────────────

/**
 * GET /health
 * Health check básico — útil para verificar que el servidor está activo
 */
app.get('/health', (req, res) => {
  const uptimeSec = ((Date.now() - stats.start) / 1000).toFixed(1);
  res.json({
    status:   'ok',
    uptime:   `${uptimeSec}s`,
    requests: stats.requests,
    errors:   stats.errors,
    rps:      (stats.requests / Math.max(1, uptimeSec)).toFixed(2),
  });
});

/**
 * GET /fast
 * Respuesta inmediata (< 5ms).
 * Ideal para medir throughput máximo puro del servidor.
 */
app.get('/fast', (req, res) => {
  res.json({ endpoint: 'fast', message: 'Respuesta instantánea', ts: Date.now() });
});

/**
 * GET /medium
 * Latencia simulada entre 50 y 150 ms.
 * Representa un servicio con algo de I/O (base de datos ligera, caché miss).
 */
app.get('/medium', async (req, res) => {
  await randomDelay(50, 150);
  res.json({ endpoint: 'medium', message: 'Respuesta con latencia moderada', ts: Date.now() });
});

/**
 * GET /slow
 * Latencia simulada entre 300 y 700 ms.
 * Simula una consulta pesada o un servicio externo lento.
 */
app.get('/slow', async (req, res) => {
  await randomDelay(300, 700);
  res.json({ endpoint: 'slow', message: 'Respuesta con latencia alta', ts: Date.now() });
});

/**
 * GET /cpu
 * Trabajo de CPU intensivo: calcula 10 000 iteraciones de SHA-256.
 * Útil para pruebas de saturación donde el cuello de botella es CPU.
 */
app.get('/cpu', (req, res) => {
  let hash = 'seed';
  for (let i = 0; i < 10_000; i++) {
    hash = crypto.createHash('sha256').update(hash + i).digest('hex');
  }
  res.json({ endpoint: 'cpu', hash: hash.slice(0, 16), ts: Date.now() });
});

/**
 * GET /unstable
 * Responde con éxito el 80% del tiempo y con error 500 el 20% restante.
 * Permite visualizar la tasa de errores bajo carga.
 */
app.get('/unstable', async (req, res) => {
  await randomDelay(20, 100);
  if (Math.random() < 0.2) {
    stats.errors++;
    return res.status(500).json({ error: 'Error simulado (20% prob)', ts: Date.now() });
  }
  res.json({ endpoint: 'unstable', message: 'Éxito', ts: Date.now() });
});

/**
 * GET /echo?delay=<ms>
 * Latencia configurable por query param (max 5000ms para seguridad).
 * Permite experimentos ad-hoc.
 */
app.get('/echo', async (req, res) => {
  const delay = Math.min(parseInt(req.query.delay, 10) || 0, 5000);
  await sleep(delay);
  res.json({ endpoint: 'echo', delay, ts: Date.now() });
});

// ─── Arranque ──────────────────────────────────────────────────────────────────

app.listen(PORT, () => {
  console.log(`\n🚀 Servidor de demo corriendo en http://localhost:${PORT}`);
  console.log('\nEndpoints disponibles:');
  console.log('  GET /health   → estado del servidor');
  console.log('  GET /fast     → respuesta inmediata');
  console.log('  GET /medium   → latencia 50-150ms');
  console.log('  GET /slow     → latencia 300-700ms');
  console.log('  GET /cpu      → carga de CPU intensiva');
  console.log('  GET /unstable → 20% de errores 500');
  console.log('  GET /echo?delay=<ms> → latencia configurable\n');
});
