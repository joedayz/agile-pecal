import { defineConfig } from 'vitest/config';

export default defineConfig({
  test: {
    // Muestra cada test con su nombre en la terminal
    reporter: 'verbose',
    // Cobertura de código (npm run coverage)
    coverage: {
      provider: 'v8',
      reporter: ['text', 'html'],
    },
  },
});
