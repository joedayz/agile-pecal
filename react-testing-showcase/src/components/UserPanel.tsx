import { useCallback, useEffect, useState } from "react";
import { fetchCurrentUser } from "../api/userApi";
import { BalanceCard } from "./BalanceCard";
import "./UserPanel.css";

type Status = "loading" | "ok" | "error";

export function UserPanel() {
  const [status, setStatus] = useState<Status>("loading");
  const [name, setName] = useState<string>("");
  const [balance, setBalance] = useState<number>(0);
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [retryToken, setRetryToken] = useState(0);

  const load = useCallback(() => {
    const ac = new AbortController();
    setStatus("loading");
    setErrorMessage("");
    fetchCurrentUser(ac.signal)
      .then((user) => {
        setName(user.name);
        setBalance(user.balanceSol);
        setStatus("ok");
      })
      .catch((e: unknown) => {
        if (ac.signal.aborted) return;
        setStatus("error");
        setErrorMessage(e instanceof Error ? e.message : "Error desconocido");
      });
    return () => ac.abort();
  }, []);

  useEffect(() => {
    const cleanup = load();
    return cleanup;
  }, [load, retryToken]);

  const handleRetry = () => setRetryToken((n) => n + 1);

  return (
    <section className="user-panel" aria-busy={status === "loading"}>
      {status === "loading" && (
        <p className="user-panel__state" data-testid="loading">
          Cargando perfil…
        </p>
      )}
      {status === "error" && (
        <div className="user-panel__error-block" data-testid="error">
          <div className="user-panel__error" role="alert">
            No pudimos cargar tu cuenta: {errorMessage}
          </div>
          <button type="button" className="user-panel__retry" onClick={handleRetry}>
            Reintentar
          </button>
        </div>
      )}
      {status === "ok" && (
        <div className="user-panel__content" data-testid="profile-ready">
          <p className="user-panel__greeting">
            Hola, <strong>{name}</strong>
          </p>
          <BalanceCard label="Saldo disponible" amountSol={balance} highlight />
        </div>
      )}
    </section>
  );
}
