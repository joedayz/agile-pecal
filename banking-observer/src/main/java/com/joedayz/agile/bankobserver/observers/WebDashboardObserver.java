package com.joedayz.agile.bankobserver.observers;

import com.joedayz.agile.bankobserver.Account;
import com.joedayz.agile.bankobserver.BalanceEvent;
import com.joedayz.agile.bankobserver.BalanceObserver;
import com.joedayz.agile.bankobserver.ConsoleTheme;
import com.joedayz.agile.bankobserver.MoneyFormat;

public class WebDashboardObserver implements BalanceObserver {
    @Override
    public void onBalanceChanged(Account account, BalanceEvent event) {
        String spark = event.delta() >= 0 ? "▲" : "▼";
        String color = event.delta() >= 0 ? ConsoleTheme.GREEN : ConsoleTheme.RED;
        System.out.println(
                ConsoleTheme.BLUE + ConsoleTheme.BOLD + "🖥  Dashboard web" + ConsoleTheme.RESET
                        + "  cuenta " + account.getId()
                        + "  │  " + color + spark + " " + MoneyFormat.soles(Math.abs(event.delta()))
                        + ConsoleTheme.RESET
                        + "  →  saldo " + ConsoleTheme.BOLD + MoneyFormat.soles(event.newBalance()) + ConsoleTheme.RESET
        );
    }
}
