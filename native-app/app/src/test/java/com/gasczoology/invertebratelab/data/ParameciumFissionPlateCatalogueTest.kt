package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ParameciumFissionPlateCatalogueTest {
    @Test fun platesAreMappedToCorrectStages() {
        assertEquals(listOf("F01"), ParameciumFissionPlateCatalogue.forStage("mother").map { it.id })
        assertEquals(listOf("F02", "F03"), ParameciumFissionPlateCatalogue.forStage("nuclei").map { it.id })
        assertTrue(ParameciumFissionPlateCatalogue.forStage("furrow").isEmpty())
        assertTrue(ParameciumFissionPlateCatalogue.forStage("daughters").isEmpty())
    }
}
