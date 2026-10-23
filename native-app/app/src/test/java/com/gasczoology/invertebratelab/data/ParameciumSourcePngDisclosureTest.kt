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
        assertTrue(code.contains("ParameciumProcess.BINARY_FISSION to null"))
        assertTrue(code.contains("ParameciumProcess.CONJUGATION to null"))
        assertFalse(code.contains("Canvas("))
    }

    @Test fun readerEnlargesOnlyPresentRasterAndShowsProvenanceWarning() {
        val code = File("src/main/java/com/gasczoology/invertebratelab/ui/ParameciumTextbookReader.kt").readText()
        assertTrue(code.contains("r14-enlarge-concept-png-"))
        assertTrue(code.contains("r14-concept-source-warning-"))
        assertTrue(code.contains("RequiredTypeStudyFullImage("))
        assertTrue(code.contains("if (bitmap != null)"))
        assertTrue(code.contains("r14-concept-png-missing-"))
    }
}
