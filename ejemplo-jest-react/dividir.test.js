const { dividir } = require('./dividir');

test('lanza error si divide por cero', () => {

  expect(() => {
    dividir(10, 0);
  }).toThrow('No se puede dividir');

});
