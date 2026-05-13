# Demo Postman: contratos REST + Newman

## Contenido

| Pieza | Descripción |
|--------|----------------|
| `contracts/openapi.yaml` | Contrato REST de referencia (OpenAPI 3). |
| `api/server.mjs` | API mínima Express (`GET /health`, `GET/POST /libros`, `GET /libros/:id`). |
| `postman/Libros-API.postman_collection.json` | Colección con **tests** por request (Chai vía `pm.expect`). |
| `postman/Local.postman_environment.json` | Entorno con variable `baseUrl` → `http://localhost:3030`. |
| `.github/workflows/postman-newman.yml` (en la raíz del repo) | CI: `npm ci` + **Newman** levantando la API con `start-server-and-test`. |

## Postman (GUI)

1. **Importar**: *File → Import* y elige la colección y el entorno (JSON en `postman/`).
2. Selecciona el entorno **Local** arriba a la derecha.
3. Arranca la API: `npm start` (puerto **3030**).
4. Ejecuta la colección con *Run collection*.

Los tests comprueban códigos HTTP, forma del JSON (`data`, `error`) y encadenan **Crear libro** → variable `ultimoLibroId` → **Obtener libro recién creado**.

## Newman (CLI)

Con la API ya en marcha:

```bash
cd postman-rest-demo
npm install
npm start   # en otra terminal
npm run test:contracts
```

Un solo comando (sube la API, corre Newman y la apaga) — útil en CI y en local:

```bash
npm run test:contracts:ci
```

## Integración continua

El workflow `postman-newman.yml` se ejecuta cuando cambian archivos bajo `postman-rest-demo/` o el propio workflow.
