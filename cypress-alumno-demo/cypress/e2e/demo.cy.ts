/**
 * Ejemplo sencillo para aula:
 * - `data-cy` en el HTML = selectores estables (mejor que clases CSS que cambian con diseño).
 * - Cada `it` comprueba una pequeña historia de usuario.
 */
describe("Página de práctica", () => {
  beforeEach(() => {
    cy.visit("/");
  });

  it("muestra el título", () => {
    cy.get("[data-cy=titulo]").should("contain.text", "Práctica Cypress");
  });

  it("incrementa el contador al pulsar Sumar", () => {
    cy.get("[data-cy=contador]").should("have.text", "0");
    cy.get("[data-cy=btn-sumar]").click();
    cy.get("[data-cy=contador]").should("have.text", "1");
    cy.get("[data-cy=btn-sumar]").click().click();
    cy.get("[data-cy=contador]").should("have.text", "3");
  });

  it("saluda con el nombre escrito", () => {
    cy.get("[data-cy=nombre]").type("María");
    cy.get("[data-cy=btn-saludar]").click();
    cy.get("[data-cy=mensaje]")
      .should("be.visible")
      .and("contain.text", "Hola, María");
  });

  it("avisa si el nombre está vacío", () => {
    cy.get("[data-cy=btn-saludar]").click();
    cy.get("[data-cy=mensaje]").should("contain.text", "Escribe un nombre");
  });
});
