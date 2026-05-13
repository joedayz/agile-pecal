// ─── api.test.js ──────────────────────────────────────────────────────────────
//
// CONCEPTOS QUE CUBRE ESTE ARCHIVO
// ─────────────────────────────────
// 1. Tests asíncronos   → async / await dentro de it()
// 2. vi.fn()            → función mock (espía)
// 3. vi.stubGlobal()    → reemplaza globales (fetch) con un mock
// 4. afterEach          → limpieza después de cada test
// 5. Matchers           → resolves, rejects, toHaveBeenCalledWith
// ─────────────────────────────────────────────────────────────────────────────

import { describe, it, expect, vi, afterEach } from 'vitest';
import { obtenerUsuario } from './api.js';

// Mockeamos fetch para que los tests NO hagan llamadas reales de red.
// vi.stubGlobal reemplaza la variable global 'fetch' con nuestra función mock.
const fetchMock = vi.fn();
vi.stubGlobal('fetch', fetchMock);

afterEach(() => {
  // Limpia las llamadas registradas después de cada test
  vi.clearAllMocks();
});

describe('obtenerUsuario()', () => {
  it('retorna los datos del usuario cuando la respuesta es ok', async () => {
    const usuarioFalso = { id: 1, name: 'Leanne Graham', email: 'leanne@gmail.com' };

    // Configuramos qué devolverá el fetch mockeado
    fetchMock.mockResolvedValue({
      ok: true,
      json: async () => usuarioFalso,
    });

    // Tests asíncronos: usa async/await igual que en código de producción
    const resultado = await obtenerUsuario(1);

    // El resultado debe coincidir con el objeto falso
    expect(resultado).toEqual(usuarioFalso);

    // Verificamos que fetch fue llamado con la URL correcta
    expect(fetchMock).toHaveBeenCalledWith(
      'https://jsonplaceholder.typicode.com/users/1'
    );
  });

  it('lanza error cuando la respuesta HTTP no es ok', async () => {
    fetchMock.mockResolvedValue({ ok: false, status: 404 });

    // expect(...).rejects.toThrow: test asíncrono que espera un rechazo
    await expect(obtenerUsuario(99)).rejects.toThrow('Error HTTP: 404');
  });

  it('lanza error cuando la red falla', async () => {
    fetchMock.mockRejectedValue(new Error('Network error'));

    await expect(obtenerUsuario(1)).rejects.toThrow('Network error');
  });
});
