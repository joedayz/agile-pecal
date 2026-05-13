# React + Vitest: pruebas para el aula

Proyecto mínimo pero **completo** para enseñar en front (y el mismo stack sirve en Node con Vitest):

| Tema | Dónde |
|------|--------|
| **Unitarias** (lógica pura) | `src/lib/formatSol.test.ts` |
| **Unitarias** (módulo con `fetch` mockeado) | `src/api/userApi.test.ts` — `vi.spyOn(globalThis, "fetch")` |
| **Integración** (React + red simulada) | `src/components/UserPanel.integration.test.tsx`, `src/App.integration.test.tsx` — **MSW** (`src/test/server.ts`) |
| **Snapshots con criterio** | `src/components/BalanceCard.test.tsx` — snapshot solo tras aserciones de roles/texto; comentario en el test |
| **Cobertura como apoyo** | `npm run test:coverage` — configurada en `vite.config.ts` **sin** umbrales al 100% |

## Scripts

```bash
npm install
npm run dev          # UI en http://localhost:5173
npm run test         # Vitest en modo watch
npm run test:run     # CI / una pasada
npm run test:coverage
npm run test:ui      # UI de Vitest (opcional)
```

## Filosofía (para comentar en clase)

1. **Mocks**: sirven para aislar unidades (ej. `fetch`) o para simular APIs de forma realista (**MSW** intercepta las mismas URLs que usa el código).
2. **Snapshots**: útiles para componentes **hoja** y estables; deben ir **acompañados** de `getByRole` / textos que expresen el comportamiento. Si el snapshot rompe a menudo por detalles irrelevantes, hay que endurecer el criterio o dejar de snapshotear ese trozo.
3. **Cobertura**: ayuda a ver huecos y módulos nunca ejecutados; **no** es meta en sí (evitar “pintar verde” con tests frágiles).

## Stack

- React 19 + TypeScript + Vite 6  
- Vitest + Testing Library + jsdom  
- MSW 2 para integración sin tocar `fetch` en producción  

## Desarrollo sin backend

`vite.config.ts` incluye un middleware **solo en `vite dev`** que responde `GET /api/me` con JSON de ejemplo. En producción conviene quitar ese plugin o sustituirlo por un proxy a tu API real.

## Detalle pedagógico: moneda en tests

`Intl` puede insertar espacios no separables entre `S/` y el número. Los tests comparan texto con `normalizeIntlText` (`src/test/normalizeIntlText.ts`) para no depender de un único tipo de espacio.
