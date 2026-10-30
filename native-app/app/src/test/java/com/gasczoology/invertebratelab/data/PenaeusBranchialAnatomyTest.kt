package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Dendrobranchiate morphology and attachment terms cross-checked against:
 * Foster & Howse, Tissue Cell (1978), DOI: 10.1016/0040-8166(78)90008-3.
 * Scholtz & Richter, Arthropod Systematics & Phylogeny, Decapod gill origins.
 * Tests textual consistency, not the anatomical accuracy of embedded images.
 */
class PenaeusBranchialAnatomyTest {
    private val chapter = PenaeusBookChapter.chapter
    private val blocks = chapter.allBlocks()
    private val paragraphs = blocks.filterIsInstance<BookParagraph>()
    private val en = paragraphs.joinToString(" ") { it.text.english }
    private val ta = paragraphs.joinToString(" ") { it.text.tamil }

    @Test fun gillTypeAndWaterCurrentAreAccurateInBothLanguages() {
        assertTrue(en.contains("dendrobranchiate gills"))
        assertTrue(en.contains("paired branches"))
        assertTrue(en.contains("branched filaments"))
        assertTrue(en.contains("scaphognathite"))
        assertTrue(ta.contains("கிளைபிரிந்த செவுள்கள்"))
        assertTrue(ta.contains("மைய அச்சு"))
        assertTrue(ta.contains("ஸ்கேஃபோக்னதைட்"))
        assertFalse("Not all gills are limb-base outgrowths",
            en.contains("gills, which are feathery outgrowths from the bases of the thoracic limbs"))
        assertFalse(ta.contains("மார்புக் கால்களின் அடிகளிலிருந்து வளரும் இறகு போன்ற"))
    }

    @Test fun allThreeAttachmentCategoriesHavePreciseBilingualDescriptions() {
        val points = blocks.filterIsInstance<BookKeyPoints>().single {
            it.title.english == "Gill attachment is not uniform"
        }
        assertEquals(3, points.points.size)
        assertTrue(points.points[0].english.contains("coxa"))
        assertTrue(points.points[1].english.contains("articular membrane"))
        assertTrue(points.points[2].english.contains("lateral thoracic body wall"))
        assertTrue(points.points.all { it.tamil.isNotBlank() })
        assertTrue(points.points[0].tamil.contains("காக்சா"))
        assertTrue(points.points[1].tamil.contains("மூட்டு இணைப்புச் சவ்வு"))
        assertTrue(points.points[2].tamil.contains("பக்கவாட்டு உடற்சுவர்"))
    }

    @Test fun gallDefinitionAndPreviousAssessmentStructureRemainIntact() {
        val d = chapter.definitions.single { it.term.english == "Dendrobranchiate gill" }
        assertTrue(d.meaning.english.contains("subdivide into branched filaments"))
        assertTrue(d.meaning.tamil.contains("மைய அச்சிலிருந்து"))
        assertTrue(chapter.reviewQuestionIds.isNotEmpty())
        assertTrue(chapter.quickChecks.size >= 3)
        assertTrue(blocks.filterIsInstance<BookFigure>().any { it.slot == "penaeus/internal" })
        assertTrue(blocks.filterIsInstance<BookFigure>().any { it.slot == "penaeus/larval-development" })
    }
}
