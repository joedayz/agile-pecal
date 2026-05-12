package com.joedayz.agile.repositoryorbital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemorySpecimenRepositoryTest {

    private InMemorySpecimenRepository repo;
    private UUID idA;
    private UUID idB;

    @BeforeEach
    void setUp() {
        repo = new InMemorySpecimenRepository();
        idA = UUID.randomUUID();
        idB = UUID.randomUUID();
        repo.save(new Specimen(idA, "Alpha", "Sector-1", ThreatLevel.BENIGN));
        repo.save(new Specimen(idB, "Bravo", "Sector-1", ThreatLevel.QUARANTINE));
    }

    @Test
    void findById_roundTrip() {
        assertEquals("Alpha", repo.findById(idA).map(Specimen::designation).orElseThrow());
    }

    @Test
    void findByOriginSector_filters() {
        List<Specimen> s1 = repo.findByOriginSector("Sector-1");
        assertEquals(2, s1.size());
        assertTrue(repo.findByOriginSector("Vacío").isEmpty());
    }

    @Test
    void findByThreatLevelAtLeast_includes_quarantine_when_floor_caution() {
        List<Specimen> list = repo.findByThreatLevelAtLeast(ThreatLevel.CAUTION);
        assertEquals(1, list.size());
        assertEquals("Bravo", list.get(0).designation());
    }

    @Test
    void deleteById_removes() {
        assertTrue(repo.deleteById(idA));
        assertFalse(repo.findById(idA).isPresent());
    }
}
