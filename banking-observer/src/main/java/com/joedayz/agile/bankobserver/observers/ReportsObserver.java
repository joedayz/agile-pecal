package com.joedayz.agile.bankobserver.observers;

import com.joedayz.agile.bankobserver.Account;
import com.joedayz.agile.bankobserver.BalanceEvent;
import com.joedayz.agile.bankobserver.BalanceObserver;
import com.joedayz.agile.bankobserver.ConsoleTheme;
import com.joedayz.agile.bankobserver.MoneyFormat;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ReportsObserver implements BalanceObserver {
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    @Override
    public void onBalanceChanged(Account account, BalanceEvent event) {
        String row = String.join(
                " | ",
                FMT.format(event.at()),
                account.getId(),
                MoneyFormat.soles(event.previousBalance()),
                MoneyFormat.soles(event.newBalance()),
                (event.delta() >= 0 ? "+" : "") + MoneyFormat.soles(event.delta()),
                event.reason()
        );
        System.out.println(ConsoleTheme.MAGENTA + "📊 Reportes batch" + ConsoleTheme.RESET + "  " + ConsoleTheme.DIM + row + ConsoleTheme.RESET);
    }
}
