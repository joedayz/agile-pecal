package com.joedayz.agile.bankobserver;

/**
 * Observador: reacciona cuando el {@link Account} notifica un cambio de saldo.
 */
@FunctionalInterface
public interface BalanceObserver {
    void onBalanceChanged(Account account, BalanceEvent event);
}
