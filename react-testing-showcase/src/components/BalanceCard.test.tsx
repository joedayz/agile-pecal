import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { BalanceCard } from "./BalanceCard";
import { formatSol } from "../lib/formatSol";
import { normalizeIntlText } from "../test/normalizeIntlText";

describe("BalanceCard", () => {
  it("muestra etiqueta y monto accesible", () => {
    render(<BalanceCard label="Saldo disponible" amountSol={1500.5} />);

    expect(screen.getByRole("heading", { name: "Saldo disponible" })).toBeInTheDocument();
    const amount = screen.getByTestId("balance-card").querySelector(".balance-card__amount")?.textContent ?? "";
    expect(normalizeIntlText(amount)).toBe(normalizeIntlText(formatSol(1500.5)));
  });

  /**
   * Snapshot con criterio (no “snapshot por snapshot”):
   * - Solo en componente presentacional pequeño y estable.
   * - Siempre junto a aserciones semánticas arriba; el snapshot detecta regresiones de estructura,
   *   no sustituye pensar en qué debe comportarse el UI.
   * - Si el cambio visual es intencional: actualizar con `npm run test:run -- -u` revisando el diff.
   */
  it("estructura visual estable (snapshot)", () => {
    const { container } = render(
      <BalanceCard label="Saldo a la vista" amountSol={250} highlight />,
    );

    expect(container.firstChild).toMatchSnapshot();
  });
});
