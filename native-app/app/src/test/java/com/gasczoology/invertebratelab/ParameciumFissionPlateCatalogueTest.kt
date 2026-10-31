package com.gasczoology.invertebratelab

import com.gasczoology.invertebratelab.data.ParameciumFissionPlateCatalogue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/** Guard against accidentally hiding or mislabelling committed F01-F03 plates. */
class ParameciumFissionPlateCatalogueTest {
    @Test fun sourcePlatesRetainTheirStageAndBilingualCaptions() {
        val plates = ParameciumFissionPlateCatalogue.plates
        assertEquals(listOf("F01", "F02", "F03"), plates.map { it.id })
        assertEquals(listOf("mother", "nuclei", "nuclei"), plates.map { it.stageId })
        assertTrue(plates.all { it.sourceFile.endsWith("_PROVISIONAL.png") })
        assertTrue(plates.all { it.englishCaption.isNotBlank() && it.tamilCaption.isNotBlank() })
    }

    @Test fun noLaterStagePlateIsMisrepresentedAsExisting() {
        assertTrue(ParameciumFissionPlateCatalogue.forStage("furrow").isEmpty())
        assertTrue(ParameciumFissionPlateCatalogue.forStage("daughters").isEmpty())
    }
}
