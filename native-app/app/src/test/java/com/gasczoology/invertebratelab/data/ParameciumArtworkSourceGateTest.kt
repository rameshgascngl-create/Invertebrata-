package com.gasczoology.invertebratelab.data

import com.gasczoology.invertebratelab.ui.ParameciumProcessPngIndex
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Academic fail-closed gate for unverified reproduction artwork.
 *
 * A Wikimedia Commons source with a reusable licence is NOT automatically an
 * approved anatomy plate. The approved asset mapping must be changed only with
 * a committed PNG, source-rights record and independent zoology/Tamil review.
 */
class ParameciumArtworkSourceGateTest {
    @Test fun reproductionDoesNotReuseUnrelatedAnatomyOrUnreviewedConjugationPoster() {
        val routes = ParameciumProcessPngIndex.byProcess
        assertEquals(ParameciumProcess.entries.toSet(), routes.keys)
        assertEquals("unit1/reference/paramecium_parker_fission_reference", routes[ParameciumProcess.BINARY_FISSION])
        assertEquals("unit1/reference/paramecium_conjugation_reference", routes[ParameciumProcess.CONJUGATION])
    }

    @Test fun existingPhysiologySubjectsHaveDistinctImageRoutes() {
        val routes = ParameciumProcessPngIndex.byProcess
        val cilia = routes[ParameciumProcess.CILIARY_MOTION]
        val osmotic = routes[ParameciumProcess.OSMOREGULATION]
        val feeding = routes[ParameciumProcess.FEEDING]
        assertNotEquals(cilia, osmotic)
        assertNotEquals(cilia, feeding)
        assertNotEquals(osmotic, feeding)
    }
}
