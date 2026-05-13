# Demo Cypress (aula)

Ejemplo **mínimo**: una página con Vite (HTML + JS) y unas pocas pruebas E2E.

## Ideas para comentar en clase

1. **`data-cy`** (o `data-testid`): atributos solo para pruebas. Así los selectores no dependen del texto visible ni de clases CSS que el diseño cambia a menudo.
2. **`describe` / `it`**: agrupan comportamiento y leen como historias cortas.
3. **`cy.visit` → acción → aserción`**: patrón Arrange–Act–Assert en el navegador.

## Comandos

Terminal 1 — sirve la app:

```bash
npm install
npm run dev
```

Terminal 2 — Cypress interactivo (recomendado la primera vez):

```bash
npm run cy:open
```

Elegir **E2E Testing** y ejecutar `demo.cy.ts`.

Una sola terminal — levanta el servidor, corre pruebas y apaga (útil en CI o evaluaciones):

```bash
npm run test:e2e
```

## Archivos importantes

| Ruta | Rol |
|------|-----|
| `index.html` + `src/app.js` | App de ejemplo |
| `cypress/e2e/demo.cy.ts` | Pruebas |
| `cypress.config.ts` | `baseUrl` apuntando a Vite (`http://localhost:5173`) |

## Siguiente paso (opcional)

En proyectos React reales suele usarse el mismo `baseUrl` contra `npm run dev` del front y más pantallas en `cypress/e2e/`.
