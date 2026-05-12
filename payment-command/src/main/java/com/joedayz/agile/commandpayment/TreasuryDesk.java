package com.joedayz.agile.commandpayment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Receptor (Receiver): sabe <em>cómo</em> aplicar la lógica real de negocio.
 * Los comandos no duplican reglas; solo delegan aquí.
 */
public class TreasuryDesk {

    private final List<String> bitacora = new ArrayList<>();

    public void aprobar(Payment pago) {
        if (pago.getEstado() != PaymentStatus.PENDIENTE) {
            bitacora.add("Ignorado aprobar " + pago.getId() + " (estado=" + pago.getEstado() + ")");
            return;
        }
        pago.setEstado(PaymentStatus.APROBADO);
        bitacora.add("APROBADO " + pago.getId() + " → " + pago.getBeneficiario()
                + " por " + pago.getImporte() + " €");
    }

    public void rechazar(Payment pago, String motivo) {
        if (pago.getEstado() != PaymentStatus.PENDIENTE) {
            bitacora.add("Ignorado rechazar " + pago.getId() + " (estado=" + pago.getEstado() + ")");
            return;
        }
        pago.setEstado(PaymentStatus.RECHAZADO);
        bitacora.add("RECHAZADO " + pago.getId() + ": " + motivo);
    }

    /** Regla de ejemplo: montos enormes requieren doble revisión (la demo lo usa en un comando compuesto). */
    public boolean requiereSegundaFirma(BigDecimal importe) {
        return importe.compareTo(new BigDecimal("10000")) > 0;
    }

    public List<String> getBitacora() {
        return Collections.unmodifiableList(bitacora);
    }
}
