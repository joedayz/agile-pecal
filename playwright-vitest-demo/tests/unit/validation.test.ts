import { describe, expect, it } from "vitest";
import { validateInscripcion, type FormFields } from "../../src/validation";

function base(over: Partial<FormFields> = {}): FormFields {
  return {
    nombre: "Ana García",
    email: "ana@ejemplo.org",
    telefono: "",
    mensaje: "Quiero aprender pruebas automatizadas en el taller.",
    aceptoPolitica: true,
    ...over,
  };
}

describe("validateInscripcion", () => {
  it("acepta un formulario válido", () => {
    const { valid, errors } = validateInscripcion(base());
    expect(valid).toBe(true);
    expect(errors).toEqual({});
  });

  it("exige nombre", () => {
    const { valid, errors } = validateInscripcion(base({ nombre: "" }));
    expect(valid).toBe(false);
    expect(errors.nombre).toMatch(/obligatorio/i);
  });

  it("exige al menos 2 caracteres en el nombre", () => {
    const { valid, errors } = validateInscripcion(base({ nombre: "A" }));
    expect(valid).toBe(false);
    expect(errors.nombre).toMatch(/2 caracteres/i);
  });

  it("rechaza correo vacío o inválido", () => {
    expect(validateInscripcion(base({ email: "" })).errors.email).toMatch(/obligatorio/i);
    expect(validateInscripcion(base({ email: "sin-arroba" })).errors.email).toMatch(/válido/i);
  });

  it("teléfono opcional vacío es válido", () => {
    const { valid, errors } = validateInscripcion(base({ telefono: "" }));
    expect(valid).toBe(true);
    expect(errors.telefono).toBeUndefined();
  });

  it("teléfono con formato incorrecto si se rellena", () => {
    const { valid, errors } = validateInscripcion(base({ telefono: "123" }));
    expect(valid).toBe(false);
    expect(errors.telefono).toMatch(/9 dígitos/i);
  });

  it("acepta teléfono de 9 dígitos", () => {
    const { valid } = validateInscripcion(base({ telefono: "612345678" }));
    expect(valid).toBe(true);
  });

  it("mensaje con longitud mínima y máxima", () => {
    expect(validateInscripcion(base({ mensaje: "corto" })).errors.mensaje).toMatch(/10 caracteres/i);
    const largo = "x".repeat(501);
    expect(validateInscripcion(base({ mensaje: largo })).errors.mensaje).toMatch(/500/i);
  });

  it("exige aceptar la política", () => {
    const { valid, errors } = validateInscripcion(base({ aceptoPolitica: false }));
    expect(valid).toBe(false);
    expect(errors.aceptoPolitica).toMatch(/política/i);
  });
});
