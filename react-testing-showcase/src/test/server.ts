import { http, HttpResponse } from "msw";
import { setupServer } from "msw/node";

const defaultUser = { id: "u-demo", name: "María", balanceSol: 5000 };

/**
 * MSW intercepta fetch en Node: misma API que en navegador, sin tocar el código de producción.
 * Útil para pruebas de integración “casi E2E” del front.
 */
export const server = setupServer(
  http.get("/api/me", () => HttpResponse.json(defaultUser)),
);

export { defaultUser };
