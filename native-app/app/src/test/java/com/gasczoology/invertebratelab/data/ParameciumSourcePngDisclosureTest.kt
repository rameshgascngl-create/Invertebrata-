package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/** Guard against treating source-photo crops as independently approved plates. */
class ParameciumSourcePngDisclosureTest {
    @Test fun simulatorDisclosesSourceOriginAndKeepsMissingReproductionArtBlocked() {
        val code = File("src/main/java/com/gasczoology/invertebratelab/ui/ParameciumProcessPng.kt").readText()
        assertTrue(code.contains("Source-derived photographed pencil PNG crop"))
        assertTrue(code.contains("ParameciumProcess.CILIARY_MOTION to \"unit1/paramecium_external\""))
        assertTrue(code.contains("ParameciumProcess.OSMOREGULATION to \"unit1/paramecium_vacuole\""))
        assertTrue(code.contains("ParameciumProcess.FEEDING to \"unit1/paramecium_oral\""))
        for (stem in listOf("paramecium_external", "paramecium_vacuole", "paramecium_oral")) {
            assertTrue("Missing bundled raster: $stem", File("src/main/assets/unit1/$stem.png").isFile)
        }
        assertTrue(code.contains("ParameciumProcess.BINARY_FISSION to \"unit1/reference/paramecium_parker_fission_reference\""))
        assertTrue(code.contains("ParameciumProcess.CONJUGATION to \"unit1/reference/paramecium_conjugation_reference\""))
        assertFalse(code.contains("Canvas("))
    }

    @Test fun readerEnlargesOnlyPresentRasterAndShowsProvenanceWarning() {
        val code = File("src/main/java/com/gasczoology/invertebratelab/ui/ParameciumTextbookReader.kt").readText()
        assertTrue(code.contains("r14-enlarge-concept-png-"))
        assertTrue(code.contains("r14-concept-source-warning-"))
        assertTrue(code.contains("RequiredTypeStudyFullImage("))
        assertTrue(code.contains("if (bitmap != null)"))
        assertTrue(code.contains("r14-concept-png-missing-"))
        assertTrue(code.contains("\"vacuole\" -> \"unit1/paramecium_vacuole\""))
        assertTrue(code.contains("\"fission\" -> \"unit1/reference/paramecium_parker_fission_reference\""))
        assertTrue(code.contains("\"conjugation\" -> \"unit1/reference/paramecium_conjugation_reference\""))
    }
}
