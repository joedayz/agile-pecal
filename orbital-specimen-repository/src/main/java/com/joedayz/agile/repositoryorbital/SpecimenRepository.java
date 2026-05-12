package com.joedayz.agile.repositoryorbital;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Contrato tipo colección: el dominio no sabe si detrás hay RAM, PostgreSQL o una API de la Tercera Flota.
 */
public interface SpecimenRepository {

    void save(Specimen specimen);

    Optional<Specimen> findById(UUID id);

    List<Specimen> findAll();

    boolean deleteById(UUID id);

    List<Specimen> findByOriginSector(String sector);

    List<Specimen> findByThreatLevelAtLeast(ThreatLevel minimum);
}
