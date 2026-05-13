import { render, screen, waitFor } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { UserPanel } from "./UserPanel";
import { defaultUser } from "../test/server";
import { formatSol } from "../lib/formatSol";
import { normalizeIntlText } from "../test/normalizeIntlText";

describe("UserPanel (integración aislada)", () => {
  it("integra fetch + estado sin montar toda la App", async () => {
    render(<UserPanel />);

    await waitFor(() => {
      expect(screen.getByTestId("profile-ready")).toBeInTheDocument();
    });

    const amount = screen.getByTestId("balance-card").querySelector(".balance-card__amount")?.textContent ?? "";
    expect(normalizeIntlText(amount)).toBe(normalizeIntlText(formatSol(defaultUser.balanceSol)));
  });
});
