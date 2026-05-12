package com.joedayz.agile.commandpayment;

/** Comando concreto: aprueba un pago pendiente vía el receptor. */
public class ApprovePaymentCommand implements PaymentCommand {

    private final TreasuryDesk tesoreria;
    private final Payment pago;

    public ApprovePaymentCommand(TreasuryDesk tesoreria, Payment pago) {
        this.tesoreria = tesoreria;
        this.pago = pago;
    }

    @Override
    public void ejecutar() {
        tesoreria.aprobar(pago);
    }
}
