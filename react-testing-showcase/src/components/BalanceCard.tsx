import { formatSol } from "../lib/formatSol";
import "./BalanceCard.css";

type Props = {
  label: string;
  amountSol: number;
  highlight?: boolean;
};

/**
 * Componente presentacional pequeño: buen candidato para snapshot
 * solo si el markup es estable y siempre acompañamos de aserciones de comportamiento.
 */
export function BalanceCard({ label, amountSol, highlight = false }: Props) {
  return (
    <article
      className={`balance-card${highlight ? " balance-card--highlight" : ""}`}
      data-testid="balance-card"
    >
      <h2 className="balance-card__label">{label}</h2>
      <p className="balance-card__amount" aria-live="polite">
        {formatSol(amountSol)}
      </p>
    </article>
  );
}
