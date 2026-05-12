package com.joedayz.agile.repositoryorbital;

import java.util.List;
import java.util.UUID;

/**
 * Lógica de dominio: solo depende del contrato {@link SpecimenRepository}, no de la implementación.
 */
public class XenobiologyLab {

    private final SpecimenRepository archive;

    public XenobiologyLab(SpecimenRepository archive) {
        this.archive = archive;
    }

    public Specimen intake(String designation, String originSector, ThreatLevel threat) {
        Specimen s = new Specimen(UUID.randomUUID(), designation, originSector, threat);
        archive.save(s);
        return s;
    }

    public List<Specimen> quarantineManifest() {
        return archive.findByThreatLevelAtLeast(ThreatLevel.CAUTION);
    }

    public String sectorBriefing(String sector) {
        List<Specimen> locals = archive.findByOriginSector(sector);
        if (locals.isEmpty()) {
            return "Sector " + sector + ": sin muestras catalogadas (o el puente de datos aún no sincronizó).";
        }
        long nasty = locals.stream().filter(x -> x.threatLevel() == ThreatLevel.QUARANTINE).count();
        return "Sector " + sector + ": " + locals.size() + " especímenes; " + nasty + " en contención máxima.";
    }

    public boolean decommission(UUID id) {
        return archive.deleteById(id);
    }
}
