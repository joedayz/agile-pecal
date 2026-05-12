package com.joedayz.agile.bankobserver;

import java.util.Locale;

public final class MoneyFormat {
    private MoneyFormat() {}

    public static String soles(double amount) {
        return String.format(Locale.US, "S/ %,.2f", amount);
    }
}
