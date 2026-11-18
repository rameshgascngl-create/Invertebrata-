package com.gasczoology.invertebratelab.data

import com.gasczoology.invertebratelab.ui.ParameciumProcessPngIndex
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
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
        assertNull("Dedicated transverse-fission PNG still missing", routes[ParameciumProcess.BINARY_FISSION])
        assertNull("Conjugation source candidate has not passed academic approval", routes[ParameciumProcess.CONJUGATION])
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
