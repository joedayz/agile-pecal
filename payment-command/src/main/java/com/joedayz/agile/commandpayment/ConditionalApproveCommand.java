package com.joedayz.agile.commandpayment;

/**
 * Comando compuesto (macro): ilustra que un Command puede orquestar varios pasos
 * sin que el invocador conozca la política de montos altos.
 */
public class ConditionalApproveCommand implements PaymentCommand {

    private final TreasuryDesk tesoreria;
    private final Payment pago;

    public ConditionalApproveCommand(TreasuryDesk tesoreria, Payment pago) {
        this.tesoreria = tesoreria;
        this.pago = pago;
    }

    @Override
    public void ejecutar() {
        if (tesoreria.requiereSegundaFirma(pago.getImporte())) {
            tesoreria.rechazar(pago, "Importe elevado: requiere segunda firma (política demo > 10.000 €)");
            return;
        }
        tesoreria.aprobar(pago);
    }
}
