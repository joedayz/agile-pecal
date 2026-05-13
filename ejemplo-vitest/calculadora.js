// ─── calculadora.js ───────────────────────────────────────────────────────────
// Funciones puras: fáciles de testear porque no dependen de estado externo.

export function suma(a, b) {
  return a + b;
}

export function resta(a, b) {
  return a - b;
}

export function multiplica(a, b) {
  return a * b;
}

export function divide(a, b) {
  if (b === 0) throw new Error('No se puede dividir entre cero');
  return a / b;
}
