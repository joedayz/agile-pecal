// ─── calculadora.test.js ──────────────────────────────────────────────────────
//
// CONCEPTOS QUE CUBRE ESTE ARCHIVO
// ─────────────────────────────────
// 1. import de helpers de Vitest  → describe, it/test, expect
// 2. describe                     → agrupa tests relacionados
// 3. it / test                    → un caso de prueba (son alias)
// 4. Matchers básicos             → toBe, toBeCloseTo, toThrow
// ─────────────────────────────────────────────────────────────────────────────

import { describe, it, expect } from 'vitest';
import { suma, resta, multiplica, divide } from './calculadora.js';

// ── 1. describe: agrupa casos relacionados ────────────────────────────────────
describe('suma()', () => {
  it('retorna la suma de dos positivos', () => {
    // expect(valor real).toBe(valor esperado)
    expect(suma(2, 3)).toBe(5);
  });

  it('suma con cero devuelve el mismo número', () => {
    expect(suma(0, 7)).toBe(7);
  });

  it('suma negativos', () => {
    expect(suma(-4, -6)).toBe(-10);
  });

  // toBeCloseTo: para decimales (evita problemas de punto flotante)
  it('0.1 + 0.2 ≈ 0.3', () => {
    expect(suma(0.1, 0.2)).toBeCloseTo(0.3);
  });
});

describe('resta()', () => {
  it('5 - 3 = 2', () => {
    expect(resta(5, 3)).toBe(2);
  });

  it('resta con negativo', () => {
    expect(resta(0, 5)).toBe(-5);
  });
});

describe('multiplica()', () => {
  it('3 × 4 = 12', () => {
    expect(multiplica(3, 4)).toBe(12);
  });

  it('cualquier número × 0 = 0', () => {
    expect(multiplica(99, 0)).toBe(0);
  });
});

describe('divide()', () => {
  it('10 / 2 = 5', () => {
    expect(divide(10, 2)).toBe(5);
  });

  // toThrow: verifica que la función lanza una excepción
  it('lanza error al dividir entre cero', () => {
    expect(() => divide(10, 0)).toThrow('No se puede dividir entre cero');
  });
});
