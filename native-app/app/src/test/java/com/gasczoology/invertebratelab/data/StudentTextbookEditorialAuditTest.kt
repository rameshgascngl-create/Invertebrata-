package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

/** Student-copy checks for every native book chapter; not independent reviewer sign-off. */
class StudentTextbookEditorialAuditTest {
    private val processPhrases = listOf(
        "academic review pending", "scientific review pending",
        "subject-expert review pending", "draft_unverified",
        "page numbers to be added", "reviewer to add",
        "மதிப்பாய்வு நிலுவை", "மதிப்பாய்வாளர் சேர்க்க",
        "சரிபார்ப்பு நிலுவையில்", "வரைவு நிலையில் உள்ளன"
    )

    @Test fun everyBookChapterContainsNoEditorialWorkflowInstructions() {
        assertTrue("Expected native textbook chapters", BookChapters.all.size >= 43)
        val findings = BookChapters.all.flatMap { chapter ->
            BookChapterContract.allTexts(chapter).flatMap { item ->
                listOf(item.english, item.tamil).flatMap { passage ->
                    processPhrases.filter { passage.contains(it, ignoreCase = true) }
                        .map { chapter.lessonId + ": " + it }
                }
            }
        }
        assertTrue("Non-teaching workflow text:\n" + findings.joinToString("\n"), findings.isEmpty())
    }

    @Test fun bookIllustrationCreditsRetainTamilAndExcludeReleaseProcessNotes() {
        val captions = SourcedQaAnatomicalPlates.credit.values.map { it.english to it.tamil } +
            BookFigureLibrary.all.map { it.credit.en to it.credit.ta } +
            listOf(SourcedLeishmaniaQaFigure.credit.en to SourcedLeishmaniaQaFigure.credit.ta)
        assertTrue(captions.size >= 11)
        val problems = captions.mapIndexedNotNull { i, (en, ta) ->
            if (en.isBlank() || ta.none { it in '\u0B80'..'\u0BFF' } ||
                processPhrases.any { en.contains(it, ignoreCase = true) || ta.contains(it, ignoreCase = true) })
                "Figure credit index " + i else null
        }
        assertTrue("Figure credit deficiencies: " + problems.joinToString(), problems.isEmpty())
        assertEquals(10, SourcedQaAnatomicalPlates.byTypeStudy.size)
    }

    @Test fun highRiskAnatomyAndLifeHistoryFiguresHaveAccurateCaptions() {
        fun fig(c: BookChapter, slot: String): BookFigure =
            c.allBlocks().filterIsInstance<BookFigure>().single { it.slot == slot }

        val leish = fig(ProtoParasitesBookChapter.chapter, "lib/leishmania-amastigote")
        assertTrue(leish.caption.english.contains("amastigote"))
        assertTrue(leish.caption.english.contains("promastigote"))
        assertTrue(leish.caption.tamil.contains("புரோமாஸ்டிகோட்"))

        val starfish = fig(AsteriasBookChapter.chapter, "asterias/larval-development")
        assertTrue(starfish.caption.english.contains("brachiolaria"))
        assertTrue(starfish.caption.tamil.contains("பிராக்கியோலாரியா"))
        assertTrue(starfish.caption.english.contains("historical", ignoreCase = true))

        val paramecium = fig(ParameciumBookChapter.chapter, "paramecium/oral-apparatus")
        assertTrue(paramecium.caption.english.contains("whole-cell"))
        assertTrue(paramecium.caption.english.contains("not all", ignoreCase = true))

        val pila = PilaBookChapter.chapter.allBlocks().filterIsInstance<BookParagraph>()
            .joinToString(" ") { it.text.english }
        assertTrue(pila.contains("outer base of each longer cephalic tentacle"))
        val ascaris = AscarisBookChapter.chapter.allBlocks().filterIsInstance<BookParagraph>()
            .joinToString(" ") { it.text.english }
        assertTrue(ascaris.contains("spicules help hold the female vulva open"))
        assertTrue(!ascaris.contains("transfers sperm to the female by inserting his spicules"))
    }

    @Test fun nativeReadersDoNotUseDeveloperStatusPhrases() {
        val root = File("src/main/java/com/gasczoology/invertebratelab/ui")
        val paths = listOf("ChapterDraftNotice.kt", "RequiredTypeStudyPlates.kt",
            "BookChapterReader.kt", "ParameciumTeachingLab.kt",
            "UnitIVPenaeusTextbook.kt", "InvertebrataApp.kt",
            "UnitIIIPlateGallery.kt", "UnitIIIPlateFullscreenViewer.kt",
            "UnitIVEarthwormReader.kt", "ParameciumTextbookReader.kt",
            "ParameciumNuclearTextbook.kt", "UnitIFlagellateTypeStudies.kt",
            "UnitIVNereisRasterPlates.kt", "UnitIProtozoaReferenceGallery.kt",
            "CiliaryPlaybackPanel.kt", "McqPracticeScreen.kt",
            "ParameciumProcessPng.kt", "ParameciumRasterAtlas.kt",
            "ParameciumWaterBalanceTextbook.kt", "UnitIEntamoebaMorphology.kt",
            "UnitIStageAtlasCanvas.kt", "ParameciumExternalCanvas.kt")
        val banned = listOf(
            "Academic review pending:", "Draft · academic review pending",
            "ACADEMIC DRAFT:", "FOUR PENCIL-ATLAS MASTERS PENDING",
            "VIEW ANATOMICAL SLOTS · Paramecium replacement plates pending",
            "Legible for student QA (minor imperfections tolerated).",
            "PNG REFERENCE — ACADEMIC REVIEW PENDING",
            "Artwork status: DRAFT_UNVERIFIED",
            "Expert review pending",
            "scientific review pending.",
            "Review status: DRAFT_UNVERIFIED.",
            "Independent biology approvals: 0/5",
            "N2.3D1 review records — biology: 0/5"
        )
        val problems = paths.flatMap { path ->
            val text = File(root, path).readText(Charsets.UTF_8)
            banned.filter(text::contains).map { path + ": " + it }
        }
        assertTrue("Outdated workflow messages: " + problems.joinToString(), problems.isEmpty())
    }
}
