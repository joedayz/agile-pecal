package com.joedayz.agile.commandpayment;

import java.math.BigDecimal;
import java.util.Objects;

/** Solicitud de pago que vive en el dominio; los comandos la manipulan vía la tesorería. */
public class Payment {

    private final String id;
    private final String beneficiario;
    private final BigDecimal importe;
    private PaymentStatus estado;

    public Payment(String id, String beneficiario, BigDecimal importe) {
        if (importe == null || importe.signum() <= 0) {
            throw new IllegalArgumentException("importe debe ser positivo");
        }
        this.id = Objects.requireNonNull(id);
        this.beneficiario = Objects.requireNonNull(beneficiario);
        this.importe = importe;
        this.estado = PaymentStatus.PENDIENTE;
    }

    public String getId() {
        return id;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public PaymentStatus getEstado() {
        return estado;
    }

    void setEstado(PaymentStatus estado) {
        this.estado = estado;
    }
}
