package com.joedayz.agile.bankobserver.observers;

import com.joedayz.agile.bankobserver.Account;
import com.joedayz.agile.bankobserver.BalanceEvent;
import com.joedayz.agile.bankobserver.BalanceObserver;
import com.joedayz.agile.bankobserver.ConsoleTheme;
import com.joedayz.agile.bankobserver.MoneyFormat;

public class SmsAlertObserver implements BalanceObserver {
    private final double alertIfBelow;

    public SmsAlertObserver(double alertIfBelow) {
        this.alertIfBelow = alertIfBelow;
    }

    @Override
    public void onBalanceChanged(Account account, BalanceEvent event) {
        boolean wasOk = event.previousBalance() >= alertIfBelow;
        boolean nowLow = event.newBalance() < alertIfBelow;
        if (!(wasOk && nowLow)) {
            return;
        }
        System.out.println(
                ConsoleTheme.YELLOW + ConsoleTheme.BOLD + "📟 SMS alertas"
                        + ConsoleTheme.RESET
                        + "  →  ALERTA: saldo " + MoneyFormat.soles(event.newBalance())
                        + " cruzó bajo " + MoneyFormat.soles(alertIfBelow)
                        + " (" + event.reason() + ")"
        );
    }
}
