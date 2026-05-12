package com.joedayz.agile.repositoryorbital;

import java.util.UUID;

/**
 * Entidad de dominio: la capa de negocio razona con esto, no con filas SQL ni JSON crudo.
 */
public record Specimen(
        UUID id,
        String designation,
        String originSector,
        ThreatLevel threatLevel) {

    public Specimen {
        if (designation == null || designation.isBlank()) {
            throw new IllegalArgumentException("designation requerido");
        }
        if (originSector == null || originSector.isBlank()) {
            throw new IllegalArgumentException("originSector requerido");
        }
    }
}
