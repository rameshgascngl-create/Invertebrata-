package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class RasterArtworkPolicyTest {
    @Test fun pngIsPreferredOverWebpWhenBothArePresent() {
        val files = setOf("unit2/sycon_plate.webp", "unit2/sycon_plate.png")
        assertEquals("unit2/sycon_plate.png",
            RasterArtworkPolicy.selectAsset("unit2/sycon_plate", files))
    }

    @Test fun validWebpWorksButSvgAndCanvasAreNeverFallbacks() {
        assertEquals("unit2/obelia_colony.webp",
            RasterArtworkPolicy.selectAsset("unit2/obelia_colony",
                setOf("unit2/obelia_colony.webp")))
        assertNull(RasterArtworkPolicy.selectAsset("unit2/obelia_colony",
            setOf("unit2/obelia_colony.svg", "unit2/obelia_colony.xml")))
        assertNull(RasterArtworkPolicy.selectAsset("unit2/missing", emptySet()))
    }

    @Test fun invalidOrUnsafeStemsAreRejected() {
        for (stem in listOf("../sycon", "unit2/../sycon", "unit2/sycon.svg",
                "unit2//sycon", "/unit2/sycon", "unit2/Sycon")) {
            assertThrows(IllegalArgumentException::class.java) {
                RasterArtworkPolicy.candidatePaths(stem)
            }
        }
    }

    @Test fun allNineTypeStudiesHaveTheSameMandatoryRasterContract() {
        assertEquals(setOf("paramecium", "sycon", "obelia", "fasciola",
            "ascaris", "earthworm", "penaeus", "pila", "asterias"),
            RasterArtworkPolicy.requiredOrganismIds)
        for (study in NativeCurriculumManifest.typeStudies) {
            val expected = study.requiredPlateIds.map {
                RasterArtworkPolicy.requiredFuturePlateStem(study,it)
            }
            assertEquals(study.requiredPlateIds.size, expected.distinct().size)
            assertTrue(expected.all { RasterArtworkPolicy.candidatePaths(it)
                .all { path -> path.endsWith(".png") || path.endsWith(".webp") } })
        }
    }

    @Test fun laterOrganismCannotBeAcceptedWithMissingPlate() {
        val study = NativeCurriculumManifest.typeStudies.single { it.organismId=="fasciola" }
        val onlyExternal = setOf("type_studies/fasciola/external.webp")
        assertThrows(IllegalStateException::class.java) {
            RasterArtworkPolicy.requireCompleteTypeStudy(study, onlyExternal)
        }
        val complete = study.requiredPlateIds.mapTo(mutableSetOf()) {
            "type_studies/fasciola/$it.webp"
        }
        assertEquals(study.requiredPlateIds.toSet(),
            RasterArtworkPolicy.requireCompleteTypeStudy(study,complete).keys)
    }

    @Test fun everyAcceptedTypeStudyMustHaveItsEntireRasterCorpus() {
        val root=File("src/main/assets")
        val available = root.walkTopDown().filter { it.isFile }
            .map { it.relativeTo(root).invariantSeparatorsPath }.toSet()
        NativeCurriculumManifest.typeStudies
            .filter { it.status==AcademicWorkStatus.ACCEPTED }
            .forEach { RasterArtworkPolicy.requireCompleteTypeStudy(it,available) }
    }
}
