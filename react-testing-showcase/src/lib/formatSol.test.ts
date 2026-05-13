import { describe, expect, it } from "vitest";
import { formatSol } from "./formatSol";

describe("formatSol (unitario puro)", () => {
  it("formatea soles en español peruano", () => {
    expect(formatSol(5000)).toMatch(/5/);
    expect(formatSol(5000)).toMatch(/000/);
    expect(formatSol(5000)).toContain("S/");
  });

  it("maneja no finitos sin lanzar", () => {
    expect(formatSol(Number.NaN)).toBe("S/ —");
    expect(formatSol(Number.POSITIVE_INFINITY)).toBe("S/ —");
  });
});
