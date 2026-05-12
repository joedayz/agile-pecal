package com.joedayz.agile.repositoryorbital;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Adaptador de persistencia “falso”: suficiente para demos y tests sin base de datos.
 */
public class InMemorySpecimenRepository implements SpecimenRepository {

    private final Map<UUID, Specimen> store = new ConcurrentHashMap<>();

    @Override
    public void save(Specimen specimen) {
        store.put(specimen.id(), specimen);
    }

    @Override
    public Optional<Specimen> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Specimen> findAll() {
        return store.values().stream()
                .sorted(Comparator.comparing(Specimen::designation))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean deleteById(UUID id) {
        return store.remove(id) != null;
    }

    @Override
    public List<Specimen> findByOriginSector(String sector) {
        String needle = sector.trim();
        return store.values().stream()
                .filter(s -> s.originSector().equalsIgnoreCase(needle))
                .sorted(Comparator.comparing(Specimen::designation))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Specimen> findByThreatLevelAtLeast(ThreatLevel minimum) {
        int floor = minimum.ordinal();
        return store.values().stream()
                .filter(s -> s.threatLevel().ordinal() >= floor)
                .sorted(Comparator.comparing(Specimen::threatLevel).reversed()
                        .thenComparing(Specimen::designation))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
