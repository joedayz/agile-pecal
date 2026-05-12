package com.joedayz.agile.commandpayment;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentCommandTest {

    @Test
    void approve_command_delegates_to_receiver() {
        TreasuryDesk desk = new TreasuryDesk();
        Payment p = new Payment("x", "ACME", new BigDecimal("10"));
        new ApprovePaymentCommand(desk, p).ejecutar();
        assertEquals(PaymentStatus.APROBADO, p.getEstado());
        assertTrue(desk.getBitacora().get(0).contains("APROBADO"));
    }

    @Test
    void invoker_runs_queue_in_order() {
        TreasuryDesk desk = new TreasuryDesk();
        Payment a = new Payment("a", "A", new BigDecimal("1"));
        Payment b = new Payment("b", "B", new BigDecimal("2"));
        PaymentInvoker invoker = new PaymentInvoker();
        invoker.encolar(new RejectPaymentCommand(desk, a, "doc"));
        invoker.encolar(new ApprovePaymentCommand(desk, b));
        invoker.ejecutarTodos();
        assertEquals(PaymentStatus.RECHAZADO, a.getEstado());
        assertEquals(PaymentStatus.APROBADO, b.getEstado());
    }

    @Test
    void conditional_rejects_high_amount() {
        TreasuryDesk desk = new TreasuryDesk();
        Payment big = new Payment("big", "Corp", new BigDecimal("20000"));
        new ConditionalApproveCommand(desk, big).ejecutar();
        assertEquals(PaymentStatus.RECHAZADO, big.getEstado());
    }
}
