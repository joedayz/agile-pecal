package com.joedayz.agile.commandpayment;

/** Comando concreto: rechaza con motivo auditado. */
public class RejectPaymentCommand implements PaymentCommand {

    private final TreasuryDesk tesoreria;
    private final Payment pago;
    private final String motivo;

    public RejectPaymentCommand(TreasuryDesk tesoreria, Payment pago, String motivo) {
        this.tesoreria = tesoreria;
        this.pago = pago;
        this.motivo = motivo;
    }

    @Override
    public void ejecutar() {
        tesoreria.rechazar(pago, motivo);
    }
}
