package com.joedayz.agile.repositoryorbital;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class XenobiologyLabTest {

    @Test
    void domain_uses_repository_as_collection_abstraction() {
        SpecimenRepository repo = new InMemorySpecimenRepository();
        XenobiologyLab lab = new XenobiologyLab(repo);

        lab.intake("X", "Z-9", ThreatLevel.BENIGN);
        lab.intake("Y", "Z-9", ThreatLevel.QUARANTINE);

        assertEquals("Sector Z-9: 2 especímenes; 1 en contención máxima.", lab.sectorBriefing("Z-9"));
        assertEquals(1, lab.quarantineManifest().size());
        assertEquals("Y", lab.quarantineManifest().get(0).designation());
    }
}
