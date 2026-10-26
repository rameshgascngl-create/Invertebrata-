package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class ParameciumNuclearSequenceTest {
    @Test fun fissionAndConjugationHaveDistinctOrderedBilingualStages() {
        val fission = ParameciumNuclearSequence.binaryFission
        val conjugation = ParameciumNuclearSequence.conjugation
        assertEquals(5, fission.size)
        assertEquals(8, conjugation.size)
        assertEquals(listOf("growth", "micronuclear-division", "macronuclear-division",
            "cortical-division", "cytokinesis"), fission.map { it.id })
        assertEquals(listOf("pairing", "meiosis", "pronuclei", "exchange",
            "synkaryon", "separation", "reorganization", "subsequent-fissions"),
            conjugation.map { it.id })
        assertTrue((fission + conjugation).all { stage ->
            stage.heading.english.isNotBlank() && stage.heading.tamil.isNotBlank() &&
                stage.explanation.english.isNotBlank() && stage.explanation.tamil.isNotBlank()
        })
    }

    @Test fun geneticExchangePrecedesReorganizationAndNoInstantCellMultiplication() {
        val stages = ParameciumNuclearSequence.conjugation
        assertTrue(stages.indexOfFirst { it.id == "exchange" } <
            stages.indexOfFirst { it.id == "synkaryon" })
        assertTrue(stages.indexOfFirst { it.id == "separation" } <
            stages.indexOfFirst { it.id == "reorganization" })
        assertTrue(stages.first { it.id == "separation" }.explanation.english.contains(
            "has not itself doubled"))
        assertTrue(stages.first { it.id == "reorganization" }.explanation.english.contains(
            "old macronuclear material"))
        assertFalse(stages.first { it.id == "pairing" }.explanation.english.contains(
            "four daughter cells"))
    }

    @Test fun caudatumConjugationPreservesMeioticAndPostzygoticNuclearCounts() {
        val stages = ParameciumNuclearSequence.conjugation.associateBy { it.id }
        val meiosis = stages.getValue("meiosis").explanation
        assertTrue(meiosis.english.contains("four haploid products"))
        assertTrue(meiosis.english.contains("three degenerate"))
        assertTrue(meiosis.tamil.contains("நான்கு"))
        val separation = stages.getValue("separation").explanation
        assertTrue(separation.english.contains("first synkaryon division"))
        val reorganization = stages.getValue("reorganization").explanation
        assertTrue(reorganization.english.contains("three successive postzygotic"))
        assertTrue(reorganization.english.contains("eight nuclei"))
        assertTrue(reorganization.english.contains("four anterior"))
        assertTrue(reorganization.english.contains("four posterior"))
        assertTrue(reorganization.english.contains("Three presumptive micronuclei"))
        assertTrue(reorganization.tamil.contains("எட்டு"))
        val descendants = stages.getValue("subsequent-fissions").explanation
        assertTrue(descendants.english.contains("four karyonides"))
        assertTrue(descendants.english.contains("eight karyonides per original pair"))
        assertTrue(descendants.english.contains("do not generalize"))
    }

    @Test fun nativeReaderUsesTextStagesAlongsideApprovedQualifiedReferencePngs() {
        val reader = File("src/main/java/com/gasczoology/invertebratelab/ui/ParameciumTextbookReader.kt")
            .readText()
        assertTrue(reader.contains("ParameciumNuclearSequence.conjugation"))
        assertTrue(reader.contains("ParameciumNuclearSequence.binaryFission"))
        assertTrue(reader.contains("paramecium-nuclear-sequence-"))
        val process = File("src/main/java/com/gasczoology/invertebratelab/ui/ParameciumProcessPng.kt")
            .readText()
        assertTrue(process.contains("ParameciumProcess.BINARY_FISSION to \"unit1/reference/paramecium_parker_fission_reference\""))
        assertTrue(process.contains("ParameciumProcess.CONJUGATION to \"unit1/reference/paramecium_conjugation_reference\""))
    }
}
