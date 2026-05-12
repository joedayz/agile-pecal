package com.joedayz.agile.repositoryorbital;

import java.util.UUID;

/**
 * Cliente de aplicación: cablea una implementación concreta del repositorio; el laboratorio ignora el cableado.
 */
public final class OrbitalArchiveDemo {

    private OrbitalArchiveDemo() {}

    public static void main(String[] args) {
        SpecimenRepository archive = new InMemorySpecimenRepository();
        XenobiologyLab lab = new XenobiologyLab(archive);

        banner();

        Specimen lichen = lab.intake("Lichen-9 (simbionte luminiscente)", "Anillo K-12", ThreatLevel.BENIGN);
        lab.intake("Cryo-worm alfa", "Periferia TRAPPIST-1", ThreatLevel.CAUTION);
        lab.intake("Entidad categoría 'Susurro'", "Nebulosa del Pensamiento", ThreatLevel.QUARANTINE);
        lab.intake("Microfauna reciclante", "Anillo K-12", ThreatLevel.BENIGN);

        System.out.println("Registrado: " + lichen.designation() + " → id " + lichen.id());
        System.out.println();
        System.out.println(lab.sectorBriefing("Anillo K-12"));
        System.out.println(lab.sectorBriefing("Nebulosa Andrómeda"));
        System.out.println();
        System.out.println("--- Manifiesto de alerta (≥ CAUTION) ---");
        lab.quarantineManifest().forEach(s ->
                System.out.println("  • " + s.designation() + " [" + s.threatLevel() + "] desde " + s.originSector()));

        System.out.println();
        System.out.println("Desincorporar muestra benigna (demo de delete)…");
        lab.decommission(lichen.id());
        System.out.println(lab.sectorBriefing("Anillo K-12"));
        System.out.println();
        System.out.println("Total indexado tras limpieza: " + archive.findAll().size() + " filas lógicas en el puente.");
    }

    private static void banner() {
        System.out.println();
        System.out.println("    ╭────────────────────────────────────────────╮");
        System.out.println("    │  ESTACIÓN PEC-AL — Archivo Xenobiológico   │");
        System.out.println("    │  Repository = embudo entre dominio y datos │");
        System.out.println("    ╰────────────────────────────────────────────╯");
        System.out.println();
    }
}
