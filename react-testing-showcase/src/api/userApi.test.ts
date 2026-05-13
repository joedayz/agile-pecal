import { describe, expect, it, vi, afterEach } from "vitest";
import { fetchCurrentUser } from "../api/userApi";

afterEach(() => {
  vi.restoreAllMocks();
});

describe("fetchCurrentUser (unitario con mock de fetch)", () => {
  it("devuelve el JSON cuando la respuesta es OK", async () => {
    const payload = { id: "99", name: "Test", balanceSol: 123.45 };
    vi.spyOn(globalThis, "fetch").mockResolvedValue({
      ok: true,
      json: () => Promise.resolve(payload),
    } as Response);

    const user = await fetchCurrentUser();

    expect(user).toEqual(payload);
    expect(fetch).toHaveBeenCalledWith(
      "/api/me",
      expect.objectContaining({ signal: undefined }),
    );
  });

  it("lanza Error con código HTTP cuando la respuesta no es OK", async () => {
    vi.spyOn(globalThis, "fetch").mockResolvedValue({
      ok: false,
      status: 418,
      json: () => Promise.resolve({}),
    } as Response);

    await expect(fetchCurrentUser()).rejects.toThrow("HTTP 418");
  });
});
