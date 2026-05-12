package com.joedayz.agile.commandpayment;

import java.math.BigDecimal;

/**
 * Cliente didáctico: la UI o el controlador solo crea comandos y se los da al invocador.
 * Mañana el mismo encolado puede venir de un botón, un mensaje JMS o un cron.
 */
public final class TreasurySimulator {

    private TreasurySimulator() {}

    public static void main(String[] args) {
        TreasuryDesk tesoreria = new TreasuryDesk();
        PaymentInvoker operador = new PaymentInvoker();

        Payment p1 = new Payment("P-100", "Proveedor Lápices S.L.", new BigDecimal("450.00"));
        Payment p2 = new Payment("P-101", "Consultora Nimbus", new BigDecimal("12500.00"));
        Payment p3 = new Payment("P-102", "Asociación Vecinos", new BigDecimal("120.50"));

        System.out.println();
        System.out.println("=== Patrón Command: cola de \"aprobar pagos\" ===");
        System.out.println("El invocador no llama a tesoreria.aprobar() directamente: encola comandos.");
        System.out.println();

        operador.encolar(new ApprovePaymentCommand(tesoreria, p1));
        operador.encolar(new ConditionalApproveCommand(tesoreria, p2));
        operador.encolar(new RejectPaymentCommand(tesoreria, p3, "Factura sin IVA desglosado (demo)"));
        operador.encolar(new ApprovePaymentCommand(tesoreria, p3));

        operador.ejecutarTodos();

        System.out.println("--- Estados finales ---");
        System.out.println(p1.getId() + " → " + p1.getEstado());
        System.out.println(p2.getId() + " → " + p2.getEstado());
        System.out.println(p3.getId() + " → " + p3.getEstado());
        System.out.println();
        System.out.println("--- Bitácora (receptor) ---");
        tesoreria.getBitacora().forEach(line -> System.out.println("  " + line));
    }
}
