package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

/** N2.3A is a biological/anatomical planning gate, not drawing acceptance. */
class ParameciumAnatomyDraftTest {
    @Test
    fun speciesScopedPlateInventoryMatchesTheExistingTypeStudyContract() {
        val plates = ParameciumAnatomyDraft.plates
        val required = NativeCurriculumManifest.typeStudies.single {
            it.organismId == "paramecium"
        }
        assertEquals("Paramecium caudatum", ParameciumAnatomyDraft.REFERENCE_SPECIES)
        assertEquals(required.requiredPlateIds, plates.map { it.plateId })
        assertEquals(4, plates.size)
        for (plate in plates) {
            assertEquals("paramecium", plate.organismId)
            assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED, plate.status)
            AnatomySpecificationContract.validate(plate)
            assertTrue(plate.features.isNotEmpty())
            assertTrue(plate.features.all {
                it.label.english.isNotBlank() && it.label.tamil.isNotBlank()
            })
            assertTrue(plate.features.all {
                it.geometryKey.startsWith("pending-geometry-") &&
                    it.touchTargetKey.startsWith("pending-touch-")
            })
            assertTrue(plate.scientificSources.isNotEmpty())
        }
    }

    @Test
    fun oralAndOsmoregulatorySubpartsMustStayDistinct() {
        val oral = ParameciumAnatomyDraft.plates.single { it.plateId == "oral-apparatus" }
        assertEquals(setOf("oral-groove", "vestibule", "cytostome",
            "cytopharynx", "forming-food-vacuole"), oral.features.map { it.id }.toSet())
        val contractile = ParameciumAnatomyDraft.plates.single {
            it.plateId == "contractile-vacuoles"
        }
        assertEquals(setOf("anterior-contractile-vacuole", "anterior-collecting-canals",
            "posterior-contractile-vacuole", "posterior-collecting-canals"),
            contractile.features.map { it.id }.toSet())
    }

    @Test
    fun pendingGeometryCannotMasqueradeAsAcceptedEvenWithACompleteReviewObject() {
        val original = ParameciumAnatomyDraft.plates.first()
        val forged = AnatomyReviewRecord(
            exactReference = "test-fixture-only",
            biologicalReviewer = "test-only",
            tamilReviewer = "test-only",
            reviewedFeatureIds = original.features.map { it.id }.toSet(),
            reviewedGeometryKeys = original.features.map { it.geometryKey }.toSet(),
        )
        val notYetValid = original.copy(
            status = AcademicWorkStatus.ACCEPTED,
            review = forged,
        )
        assertThrows(IllegalArgumentException::class.java) {
            AnatomySpecificationContract.validate(notYetValid)
        }
    }
}
