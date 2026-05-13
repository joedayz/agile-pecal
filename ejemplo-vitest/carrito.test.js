// ─── carrito.test.js ──────────────────────────────────────────────────────────
//
// CONCEPTOS QUE CUBRE ESTE ARCHIVO
// ─────────────────────────────────
// 1. beforeEach  → código que se ejecuta antes de CADA test (aísla el estado)
// 2. Matchers de objetos/arrays → toEqual, toHaveLength, toContain
// 3. test.skip   → saltar un test temporalmente
// ─────────────────────────────────────────────────────────────────────────────

import { describe, it, test, expect, beforeEach } from 'vitest';
import { Carrito } from './carrito.js';

// Variable compartida entre tests del mismo describe
let carrito;

// beforeEach: se ejecuta ANTES de cada it/test
// Garantiza que cada test empieza con un carrito limpio (test independientes)
beforeEach(() => {
  carrito = new Carrito();
});

describe('Carrito — agregar productos', () => {
  it('empieza vacío', () => {
    expect(carrito.cantidad).toBe(0);
  });

  it('agregar un producto incrementa la cantidad', () => {
    carrito.agregar({ nombre: 'Manzana', precio: 1.5 });
    expect(carrito.cantidad).toBe(1);
  });

  it('total refleja la suma de precios', () => {
    carrito.agregar({ nombre: 'Manzana', precio: 1.5 });
    carrito.agregar({ nombre: 'Pan',     precio: 2.0 });
    // toBeCloseTo: seguro con decimales
    expect(carrito.total()).toBeCloseTo(3.5);
  });
});

describe('Carrito — eliminar productos', () => {
  beforeEach(() => {
    carrito.agregar({ nombre: 'Leche',   precio: 1.2 });
    carrito.agregar({ nombre: 'Huevos',  precio: 3.0 });
    carrito.agregar({ nombre: 'Queso',   precio: 2.5 });
  });

  it('eliminar reduce la cantidad en 1', () => {
    carrito.eliminar('Leche');
    expect(carrito.cantidad).toBe(2);
  });

  it('el total disminuye al eliminar', () => {
    carrito.eliminar('Queso');
    expect(carrito.total()).toBeCloseTo(4.2);
  });

  it('vaciar deja el carrito a cero', () => {
    carrito.vaciar();
    expect(carrito.cantidad).toBe(0);
    expect(carrito.total()).toBe(0);
  });
});

// test.skip: marca un test como pendiente sin borrarlo
test.skip('TODO: validar stock disponible', () => {
  // Este test se implementará en la próxima iteración
});
