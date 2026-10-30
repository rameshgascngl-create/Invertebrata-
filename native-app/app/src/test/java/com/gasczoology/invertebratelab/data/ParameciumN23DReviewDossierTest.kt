package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** N2.3D1: read-only evidence dossier never fabricates human approvals. */
class ParameciumN23DReviewDossierTest {
    @Test
    fun allFiveSpecimenFeaturesHaveSourceTraceableUnverifiedTasks() {
        val dossier = ParameciumN23DReviewDossier
        dossier.verifyDraftAndSourceIntegrity()
        assertEquals(ParameciumExternalReviewGate.requiredIds,
            dossier.tasks.map { it.featureId })
        assertEquals(5, dossier.outstandingFeatureReviews)
        assertEquals(5, dossier.outstandingTamilReviews)
        assertTrue(dossier.pendingPhysicalDeviceReview)
        assertTrue(dossier.tasks.all { !it.geometryReviewed && !it.tamilReviewed })
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED,
            ParameciumAnatomyDraft.plates.single { it.plateId == "external-cilia" }.status)
        assertFalse(ParameciumExternalReviewGate.readyForHumanDecision(
            ParameciumExternalReviewPacket()))
    }

    @Test
    fun microscopyModalityCannotBeConfusedWithRegisteredCanvasGeometry() {
        val oral = ParameciumN23DReviewDossier.forFeature("oral-groove")
        assertEquals(ParameciumEvidenceModality.UNDERGRADUATE_TEACHING_DOCUMENT,
            oral.primary.imagingModality)
        assertEquals(
            listOf(ParameciumEvidenceModality.WHOLE_CELL_LIGHT_MICROSCOPY,
                ParameciumEvidenceModality.TRANSMISSION_ELECTRON_MICROSCOPY),
            oral.supplementary.map { it.imagingModality })
        assertTrue(oral.supplementary.all {
            it.doesNotEstablish.english.isNotBlank() &&
                it.doesNotEstablish.tamil.isNotBlank()
        })
        for (id in ParameciumExternalReviewGate.requiredIds) {
            val task = ParameciumN23DReviewDossier.forFeature(id)
            assertEquals(ParameciumExternalEvidence.records.single {
                it.featureId == id
            }.sourceUrl, task.primary.referenceUrl)
            assertTrue(task.primary.doesNotEstablish.english.isNotBlank())
            assertTrue(task.primary.doesNotEstablish.tamil.isNotBlank())
        }
        assertEquals(ParameciumEvidenceModality.TRANSMISSION_ELECTRON_MICROSCOPY,
            ParameciumN23DReviewDossier.forFeature("cytoproct").primary.imagingModality)
        assertEquals(ParameciumEvidenceModality.TRANSMISSION_ELECTRON_MICROSCOPY,
            ParameciumN23DReviewDossier.forFeature("trichocysts").primary.imagingModality)
    }

    @Test
    fun allPrimarySourcesAndLabelsAreNonEmptyWithoutInternetOrFileImports() {
        for (task in ParameciumN23DReviewDossier.tasks) {
            assertTrue(task.primary.referenceUrl.startsWith("https://"))
            assertTrue(task.primary.exactLocator.isNotBlank())
            assertTrue(task.primary.establishes.english.isNotBlank())
            assertTrue(task.primary.establishes.tamil.isNotBlank())
        }
        val existing = ParameciumAnatomyDraft.plates.single {
            it.plateId == "external-cilia"
        }
        assertTrue(existing.features.all {
            it.geometryKey.startsWith("pending-") &&
                it.touchTargetKey.startsWith("pending-") })
        assertEquals(null, existing.review)
    }
}
