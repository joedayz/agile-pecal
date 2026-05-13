import { expect, test } from "@playwright/test";

test.describe("Formulario de inscripción", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/");
  });

  test("muestra errores al enviar vacío", async ({ page }) => {
    await page.getByRole("button", { name: "Enviar inscripción" }).click();
    await expect(page.locator("#error-nombre")).toContainText(/obligatorio/i);
    await expect(page.locator("#error-email")).toContainText(/obligatorio/i);
    await expect(page.locator("#error-mensaje")).toContainText(/obligatorio/i);
    await expect(page.locator("#error-politica")).toContainText(/política/i);
    await expect(page.getByTestId("mensaje-exito")).toBeHidden();
  });

  test("rechaza correo inválido y teléfono mal formateado", async ({ page }) => {
    await page.getByLabel("Nombre completo").fill("Luis");
    await page.getByLabel("Correo electrónico").fill("no-es-correo");
    await page.getByLabel("Teléfono (opcional, 9 dígitos)").fill("12");
    await page.getByLabel(/Mensaje o expectativas/i).fill("Este mensaje tiene suficiente longitud.");
    await page.getByRole("checkbox", { name: /Acepto la política/i }).check();
    await page.getByRole("button", { name: "Enviar inscripción" }).click();
    await expect(page.locator("#error-email")).toContainText(/válido/i);
    await expect(page.locator("#error-telefono")).toContainText(/9 dígitos/i);
  });

  test("envío correcto muestra mensaje de éxito", async ({ page }) => {
    await page.getByLabel("Nombre completo").fill("María López");
    await page.getByLabel("Correo electrónico").fill("maria@ejemplo.org");
    await page.getByLabel("Teléfono (opcional, 9 dígitos)").fill("600123456");
    await page
      .getByLabel(/Mensaje o expectativas/i)
      .fill("Me interesa el taller de pruebas automatizadas.");
    await page.getByRole("checkbox", { name: /Acepto la política/i }).check();
    await page.getByRole("button", { name: "Enviar inscripción" }).click();
    await expect(page.getByTestId("mensaje-exito")).toBeVisible();
    await expect(page.getByTestId("mensaje-exito")).toContainText(/Inscripción recibida/i);
  });
});
