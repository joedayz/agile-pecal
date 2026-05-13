const { suma } = require('./suma');

/**
 * test('nombre', () => { ... }) define un caso de prueba.
 * expect(valor).toBe(esperado) comprueba igualdad estricta (===).
 */
test('suma 2 + 3 es 5', () => {
  expect(suma(2, 3)).toBe(5);
});

test('suma con cero', () => {
  expect(suma(0, 7)).toBe(7);
});

/**
 * describe agrupa tests relacionados (mejor lectura en la salida).
 */
describe('suma — más casos', () => {
  test('negativos', () => {
    expect(suma(-1, -2)).toBe(-3);
  });

  test('decimales', () => {
    expect(suma(0.1, 0.2)).toBeCloseTo(0.3);
  });
});
