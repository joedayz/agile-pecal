import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { describe, expect, it } from "vitest";
import { App } from "./App";
import { http, HttpResponse } from "msw";
import { server, defaultUser } from "./test/server";
import { formatSol } from "./lib/formatSol";
import { normalizeIntlText } from "./test/normalizeIntlText";

describe("App (integración con MSW)", () => {
  it("carga perfil y muestra saldo formateado", async () => {
    render(<App />);

    expect(screen.getByTestId("loading")).toBeInTheDocument();

    await waitFor(() => {
      expect(screen.getByTestId("profile-ready")).toBeInTheDocument();
    });

    expect(screen.getByTestId("profile-ready")).toHaveTextContent(/Hola,\s*María/);
    const amountText = screen.getByTestId("balance-card").querySelector(".balance-card__amount")?.textContent ?? "";
    expect(normalizeIntlText(amountText)).toBe(normalizeIntlText(formatSol(defaultUser.balanceSol)));
  });

  it("muestra error y recupera tras Reintentar cuando la API vuelve a OK", async () => {
    const user = userEvent.setup();

    server.use(
      http.get("/api/me", () => new HttpResponse(null, { status: 503 })),
    );

    render(<App />);

    await waitFor(() => {
      expect(screen.getByTestId("error")).toBeInTheDocument();
    });

    expect(screen.getByRole("alert")).toHaveTextContent("HTTP 503");

    server.use(http.get("/api/me", () => HttpResponse.json(defaultUser)));

    await user.click(screen.getByRole("button", { name: /reintentar/i }));

    await waitFor(() => {
      expect(screen.getByTestId("profile-ready")).toBeInTheDocument();
    });

    const amountText = screen.getByTestId("balance-card").querySelector(".balance-card__amount")?.textContent ?? "";
    expect(normalizeIntlText(amountText)).toBe(normalizeIntlText(formatSol(defaultUser.balanceSol)));
  });
});
