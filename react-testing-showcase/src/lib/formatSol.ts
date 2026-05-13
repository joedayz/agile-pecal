/** Montos en soles (Perú) — lógica pura: ideal para prueba unitaria sin DOM. */
export function formatSol(amount: number): string {
  if (!Number.isFinite(amount)) {
    return "S/ —";
  }
  return new Intl.NumberFormat("es-PE", {
    style: "currency",
    currency: "PEN",
    minimumFractionDigits: 2,
  }).format(amount);
}
