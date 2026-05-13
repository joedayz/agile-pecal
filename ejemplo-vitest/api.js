// ─── api.js ───────────────────────────────────────────────────────────────────
// Función asíncrona que llama a un servicio externo.
// En los tests la reemplazaremos con un mock para no depender de la red.

export async function obtenerUsuario(id) {
  const res = await fetch(`https://jsonplaceholder.typicode.com/users/${id}`);
  if (!res.ok) throw new Error(`Error HTTP: ${res.status}`);
  return res.json();
}
