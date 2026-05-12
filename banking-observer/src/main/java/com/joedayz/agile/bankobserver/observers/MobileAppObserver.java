package com.joedayz.agile.bankobserver.observers;

import com.joedayz.agile.bankobserver.Account;
import com.joedayz.agile.bankobserver.BalanceEvent;
import com.joedayz.agile.bankobserver.BalanceObserver;
import com.joedayz.agile.bankobserver.ConsoleTheme;
import com.joedayz.agile.bankobserver.MoneyFormat;

public class MobileAppObserver implements BalanceObserver {
    @Override
    public void onBalanceChanged(Account account, BalanceEvent event) {
        String line = ConsoleTheme.CYAN + ConsoleTheme.BOLD + "📱 App móvil"
                + ConsoleTheme.RESET + "  push → «Tu saldo ahora es " + MoneyFormat.soles(event.newBalance()) + "»";
        framed("CLIENTE: " + account.getHolderName(), line);
    }

    private static void framed(String subtitle, String body) {
        System.out.println(ConsoleTheme.DIM + "  ┌─ " + subtitle + " ─────────────────" + ConsoleTheme.RESET);
        System.out.println(ConsoleTheme.DIM + "  │ " + ConsoleTheme.RESET + body);
        System.out.println(ConsoleTheme.DIM + "  └" + "────────────────────────────────────────" + ConsoleTheme.RESET);
    }
}
