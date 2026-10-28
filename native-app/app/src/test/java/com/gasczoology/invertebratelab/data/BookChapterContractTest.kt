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
        .flatMap { s -> s.requiredPlateIds.map { s.organismId + "/" + it } }.toSet() +
        BookFigureLibrary.slots

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
                Extensions.isExtension(it)
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

    @Test fun everyLibraryFigureHasAFileAndABilingualCredit() {
        BookFigureLibrary.all.forEach { f ->
            assertTrue("No image file for ${f.slot}",
                File(assets, f.stem + ".png").isFile || File(assets, f.stem + ".webp").isFile)
            assertTrue(f.slot, f.credit.en.isNotBlank())
            assertTrue(f.slot, f.credit.ta.any { c -> c in '\u0B80'..'\u0BFF' })
        }
        assertEquals(BookFigureLibrary.all.size, BookFigureLibrary.slots.size)
    }

    @Test fun everyLibraryFigureUsedByAChapterIsRegistered() {
        BookChapters.byLesson.values.forEach { ch ->
            ch.allBlocks().filterIsInstance<BookFigure>().filter { BookFigureLibrary.isLibrarySlot(it.slot) }
                .forEach { assertTrue("${ch.lessonId}: ${it.slot}", BookFigureLibrary.stemFor(it.slot) != null) }
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

    @Test fun unitOneIntroductionUsesLayoutNumberingExactlyOnceInBothLanguages() {
        val chapter = IntroInvertebrataBookChapter.chapter
        val headings = BookLayout.rows(chapter).filterIsInstance<BookRow.SectionHeading>()
        assertEquals(10, headings.size)
        assertEquals((1..10).map { "1.$it" }, headings.map { it.number })
        val typedPrefix = Regex("""^\s*\d+(?:\.\d+)*[.)]\s+""")
        headings.forEach { row ->
            assertTrue("Duplicate English section number: ${row.heading.english}",
                !typedPrefix.containsMatchIn(row.heading.english))
            assertTrue("Duplicate Tamil section number: ${row.heading.tamil}",
                !typedPrefix.containsMatchIn(row.heading.tamil))
            assertTrue("Missing Tamil section heading: ${row.number}",
                row.heading.tamil.any { it.code in 0x0B80..0x0BFF })
        }
    }

    @Test fun unitOneProtozoaHasConciseBilingualOpeningAndKeyPoints() {
        val chapter = ProtozoaBookChapter.chapter
        assertEquals("u1-protozoa", chapter.lessonId)
        assertEquals(2, chapter.introduction.size)
        chapter.introduction.forEach { passage ->
            assertTrue("Protozoa introduction too long for an undergraduate opening",
                passage.english.split(Regex("""\s+""")).size <= 85)
            assertTrue("Missing Tamil introduction", passage.tamil.any {
                it.code in 0x0B80..0x0BFF
            })
        }
        val keyCards = chapter.allBlocks().filterIsInstance<BookKeyPoints>()
            .filter { it.title.english == "Key points for undergraduate study" }
        assertEquals(1, keyCards.size)
        assertEquals(3, keyCards.single().points.size)
        assertEquals(listOf("u1-protozoa-A5-1", "u1-protozoa-A5-2"),
            chapter.reviewQuestionIds)
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

    @Test fun extensionUnitsHaveTwoBilingualFivePointQuestionsPerChapter() {
        assertEquals(listOf(6, 7), Extensions.units.map { it.number })
        val allIds = Extensions.units.flatMap { it.chapters }.flatMap { it.a5Questions }.map { it.id }
        assertEquals(allIds.size, allIds.toSet().size)
        assertTrue(allIds.none { it in a5Ids() })
        for (unit in Extensions.units) {
            assertEquals(Extensions.chapterIdsFor(unit.number), unit.chapters.map { it.id })
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
}
