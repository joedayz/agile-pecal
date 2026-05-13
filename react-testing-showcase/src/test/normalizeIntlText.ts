/**
 * Intl (es-PE) suele insertar espacios estrechos / no separables entre símbolo y cifra.
 * En tests comparamos texto normalizado para no ser frágiles ante esos caracteres.
 */
export function normalizeIntlText(s: string): string {
  return s.replaceAll(/[\u00a0\u202f\u2007]/g, " ").replace(/\s+/g, " ").trim();
}
