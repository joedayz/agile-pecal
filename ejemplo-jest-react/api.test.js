import { obtenerUsuario } from './api';

global.fetch = jest.fn(() =>
  Promise.resolve({
    json: () =>
      Promise.resolve({ nombre: 'Jose' })
  })
);

test('obtiene usuario', async () => {

  const data = await obtenerUsuario();

  expect(data.nombre).toBe('Jose');
});
