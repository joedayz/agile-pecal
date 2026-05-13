import type { Plugin, ViteDevServer } from "vite";
import { defineConfig } from "vitest/config";
import react from "@vitejs/plugin-react";

/** En desarrollo, `/api/me` responde JSON fijo para que la UI funcione sin backend. */
function devAccountApiMock(): Plugin {
  const body = JSON.stringify({
    id: "u-demo",
    name: "María",
    balanceSol: 5000,
  });
  return {
    name: "dev-account-api-mock",
    configureServer(server: ViteDevServer) {
      server.middlewares.use("/api/me", (_req, res, _next) => {
        res.setHeader("Content-Type", "application/json");
        res.end(body);
      });
    },
  };
}

export default defineConfig({
  plugins: [react(), devAccountApiMock()],
  test: {
    globals: true,
    environment: "jsdom",
    setupFiles: "./vitest.setup.ts",
    css: true,
    coverage: {
      provider: "v8",
      reporter: ["text", "html", "json-summary"],
      reportsDirectory: "./coverage",
      /**
       * Cobertura como apoyo: excluimos entrada y tipos puros.
       * No fijamos umbrales al 100%: el objetivo es confianza + diseño testeable,
       * no optimizar un número.
       */
      exclude: [
        "node_modules/**",
        "src/main.tsx",
        "vite.config.ts",
        "**/*.d.ts",
        "**/__tests__/**",
        "**/*.test.{ts,tsx}",
        "**/test/**",
      ],
    },
  },
});
