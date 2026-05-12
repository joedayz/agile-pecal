package com.joedayz.agile.bankobserver;

import com.joedayz.agile.bankobserver.observers.MobileAppObserver;
import com.joedayz.agile.bankobserver.observers.ReportsObserver;
import com.joedayz.agile.bankobserver.observers.SmsAlertObserver;
import com.joedayz.agile.bankobserver.observers.WebDashboardObserver;

/**
 * Demostración en vivo: un solo cambio en el saldo dispara N canales sin que {@link Account}
 * conozca los detalles de cada uno (Observer + inversión de dependencias).
 */
public final class LiveBankingShowcase {
    private LiveBankingShowcase() {}

    public static void main(String[] args) throws InterruptedException {
        banner();

        Account cuenta = new Account("PE-7788-42", "María Quispe", 3_200.00);

        cuenta.subscribe(new MobileAppObserver());
        cuenta.subscribe(new WebDashboardObserver());
        cuenta.subscribe(new SmsAlertObserver(2_500.00));
        cuenta.subscribe(new ReportsObserver());

        narrate(cuenta, "Abono nómina");
        cuenta.applyMovement(1_800.00, "Abono nómina");
        pause();

        narrate(cuenta, "Compra con tarjeta");
        cuenta.applyMovement(-450.75, "Compra supermercado");
        pause();

        narrate(cuenta, "Pago de servicios (hace caer el saldo bajo el umbral SMS)");
        cuenta.applyMovement(-2_800.00, "Luz + internet");
        pause();

        narrate(cuenta, "Rescate: pequeña transferencia entrante");
        cuenta.applyMovement(150.00, "Yape recibido");
        pause();

        epilogue(cuenta);
    }

    private static void banner() {
        System.out.println();
        System.out.println(ConsoleTheme.CYAN + ConsoleTheme.BOLD
                + "  ╔═══════════════════════════════════════════════════════════╗"
                + ConsoleTheme.RESET);
        System.out.println(ConsoleTheme.CYAN + ConsoleTheme.BOLD
                + "  ║  BANCO DEL PATRÓN OBSERVER — transmisión multi-canal  ║"
                + ConsoleTheme.RESET);
        System.out.println(ConsoleTheme.CYAN + ConsoleTheme.BOLD
                + "  ╚═══════════════════════════════════════════════════════════╝"
                + ConsoleTheme.RESET);
        System.out.println(ConsoleTheme.DIM
                + "  Un solo Subject (saldo). Varios Observers. Cero acoplamiento duro."
                + ConsoleTheme.RESET);
        System.out.println();
    }

    private static void narrate(Account account, String line) {
        int n = account.observersSnapshot().size();
        System.out.println();
        System.out.println(ConsoleTheme.WHITE + ConsoleTheme.BOLD + "▶ " + line + ConsoleTheme.RESET);
        System.out.println(ConsoleTheme.DIM + "  ——— notificando a " + n + " observer(s) ———" + ConsoleTheme.RESET);
    }

    private static void pause() throws InterruptedException {
        Thread.sleep(650);
    }

    private static void epilogue(Account account) {
        System.out.println();
        System.out.println(ConsoleTheme.GREEN + ConsoleTheme.BOLD
                + "Saldo final consolidado: " + MoneyFormat.soles(account.getBalance())
                + ConsoleTheme.RESET);
        System.out.println(ConsoleTheme.DIM
                + "(En clase: pregúntales qué pasaría si mañana agregamos «Asistente de voz» "
                + "— solo un nuevo Observer, sin tocar Account.)"
                + ConsoleTheme.RESET);
        System.out.println();
    }
}
