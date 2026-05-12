package com.joedayz.agile.bankobserver;

import java.time.Instant;

/**
 * Contexto del cambio (útil para enseñar que el Subject puede pasar más que un simple double).
 */
public record BalanceEvent(
        double previousBalance,
        double newBalance,
        String reason,
        Instant at
) {
    public double delta() {
        return newBalance - previousBalance;
    }
}
