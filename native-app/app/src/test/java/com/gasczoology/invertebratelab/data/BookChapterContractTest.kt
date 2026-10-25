package com.gasczoology.invertebratelab.data

import com.google.gson.JsonParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/** Editorial floor for book-style chapters. It checks structure, not the science. */
class BookChapterContractTest {
    private val assets = File("src/main/assets")
    private val figureSlots = NativeCurriculumManifest.typeStudies
        .flatMap { s -> s.requiredPlateIds.map { s.organismId + "/" + it } }.toSet()

    /** Real A5 five-mark ids, read from the packaged corpus, not from the app code. */
    private fun a5Ids(): Set<String> = (1..5).flatMap { unit ->
        val json = File(assets, "a5/U${unit}_A5_VALIDATED.json").readText(Charsets.UTF_8)
        JsonParser.parseString(json).asJsonObject.keySet().flatMap { listOf("$it-A5-1", "$it-A5-2") }
    }.toSet()

    private val sycon = SyconBookChapter.chapter

    @Test fun syconChapterMeetsTheBookStandard() {
        BookChapterContract.validate(sycon, figureSlots, a5Ids())
    }

    @Test fun everyRegisteredBookChapterValidates() {
        BookChapters.byLesson.values.forEach { BookChapterContract.validate(it, figureSlots, a5Ids()) }
        assertTrue(BookChapters.byLesson.keys.all {
            it in NativeCurriculumManifest.a5ChapterIdsByUnit.values.flatten() ||
                InsectaExtension.isExtension(it)
        })
    }

    @Test fun everyFigureIsAPackagedRasterWithABilingualCredit() {
        val credits = PlateCreditParser.parse(
            File(assets, "type_studies/plate_credits.json").readText(Charsets.UTF_8))
        sycon.allBlocks().filterIsInstance<BookFigure>().forEach { fig ->
            val stem = "type_studies/" + fig.slot
            assertTrue("No packaged image for ${fig.slot}",
                File(assets, "$stem.png").isFile || File(assets, "$stem.webp").isFile)
            assertTrue("No credit line for ${fig.slot}", credits.containsKey(fig.slot))
        }
    }

    @Test fun numbersAreDerivedFromPositionNeverTyped() {
        assertEquals(2, BookLayout.chapterNumber("u2-sycon"))
        val rows = BookLayout.rows(sycon)
        val sections = rows.filterIsInstance<BookRow.SectionHeading>().map { it.number }
        assertEquals((1..sections.size).map { "2.$it" }, sections)
        val labels = rows.filterIsInstance<BookRow.Block>().mapNotNull { it.label }
        val figs = labels.filter { it.startsWith("Fig.") }
        assertEquals((1..figs.size).map { "Fig. 2.$it" }, figs)
        val tables = labels.filter { it.startsWith("Table") }
        assertEquals((1..tables.size).map { "Table 2.$it" }, tables)
        BookChapterContract.allTexts(sycon).forEach {
            assertTrue("Typed number in text: ${it.english.take(50)}",
                !Regex("\\b(Fig\\.|Table|Section) \\d").containsMatchIn(it.english))
        }
    }

    @Test fun glossaryIsExactlyTheDefinitionBoxes() {
        val terms = sycon.definitions.map { it.term.english }
        assertEquals(terms.size, terms.toSet().size)
        assertTrue(terms.containsAll(listOf("Osculum", "Choanocyte", "Prosopyle", "Apopyle",
            "Amphiblastula", "Stomoblastula")))
    }

    @Test fun validatorRejectsStructuralDefects() {
        val bad = { c: BookChapter -> assertThrows(IllegalArgumentException::class.java) {
            BookChapterContract.validate(c, figureSlots, a5Ids()) } }
        bad(sycon.copy(objectives = sycon.objectives.take(2)))
        bad(sycon.copy(summary = sycon.summary.take(3)))
        bad(sycon.copy(sections = sycon.sections.take(3)))
        bad(sycon.copy(reviewQuestionIds = listOf("u2-sycon-A5-9")))
        bad(sycon.copy(reviewQuestionIds = emptyList()))
        bad(sycon.copy(furtherReading = sycon.furtherReading.take(1)))
        val unknownFigure = sycon.copy(sections = sycon.sections.map { s ->
            s.copy(blocks = s.blocks.map { b ->
                if (b is BookFigure) b.copy(slot = "sycon/not-a-plate") else b }) })
        bad(unknownFigure)
        val raggedTable = sycon.copy(sections = sycon.sections.map { s ->
            s.copy(subsections = s.subsections.map { sub ->
                sub.copy(blocks = sub.blocks.map { b ->
                    if (b is BookTable) b.copy(rows = listOf(b.rows.first().drop(1))) else b }) }) })
        bad(raggedTable)
        val untranslated = sycon.copy(introduction = listOf(
            BilingualText("An English sentence copied into both languages.",
                "An English sentence copied into both languages.")) + sycon.introduction.drop(1))
        bad(untranslated)
    }

    @Test fun thinChaptersAreRejected() {
        assertThrows(IllegalArgumentException::class.java) {
            BookChapterContract.validate(sycon, figureSlots, a5Ids(), minEnglishWords = 100_000)
        }
    }

    @Test fun unitSixHasTwoBilingualFivePointQuestionsPerChapter() {
        val unit = InsectaExtension.unit
        assertEquals(9, unit.chapters.size)
        val ids = unit.chapters.flatMap { it.a5Questions }.map { it.id }
        assertEquals(18, ids.size)
        assertEquals(ids.size, ids.toSet().size)
        assertTrue(ids.none { it in a5Ids() })
        unit.chapters.forEach { ch ->
            assertEquals(ch.id, 2, ch.a5Questions.size)
            ch.a5Questions.forEach { q ->
                assertEquals(q.id, ch.id, q.chapterId)
                assertEquals(5, q.answerPoints.size)
                (listOf(q.question) + q.answerPoints).forEach {
                    assertTrue(q.id, it.english.isNotBlank() && it.tamil.isNotBlank())
                    assertTrue(q.id, it.tamil.any { c -> c in '\u0B80'..'\u0BFF' })
                }
            }
        }
    }
}
