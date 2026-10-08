package com.gasczoology.invertebratelab.data

import com.google.gson.JsonParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class ValidatedA5ParserTest {
    private val originalDir = File("../../recovery/v1.9.3/a5")
    private val bundledDir = File("src/main/assets/a5")
    private val expectedChapters = listOf(7, 9, 7, 8, 12)
    private val idPattern = Regex("""u[1-5]-[a-z0-9-]+-A5-[12]""")

    private fun original(unit: Int) =
        File(originalDir, "U" + unit + "_A5_VALIDATED.json")

    private fun bundled(unit: Int) =
        File(bundledDir, "U" + unit + "_A5_VALIDATED.json")

    private fun sources() = (1..5).associateWith { bundled(it).readText(Charsets.UTF_8) }

    @Test
    fun sourceAssetsAreExactlyIdenticalToCommittedA5Json() {
        for (unit in 1..5) {
            assertTrue("Missing original A5 source for U" + unit, original(unit).isFile)
            assertTrue("Missing packaged A5 asset for U" + unit, bundled(unit).isFile)
            assertTrue(
                "Packaged source differs byte-for-byte from authoritative U" + unit,
                original(unit).readBytes().contentEquals(bundled(unit).readBytes()),
            )
        }
    }

    @Test
    fun everyQuestionAndEveryAnswerPointIsExactInBothLanguages() {
        val units = ValidatedA5Parser.parse(sources())
        assertEquals(5, units.size)
        assertEquals(expectedChapters, units.map { it.chapters.size })
        val sourceIds = mutableSetOf<String>()
        val parsedIds = units.flatMap { it.chapters }.flatMap { it.a5Questions }.map { it.id }
        for (unit in units) {
            val json = JsonParser.parseString(original(unit.number).readText(Charsets.UTF_8)).asJsonObject
            assertEquals(json.size(), unit.chapters.size)
            assertEquals(json.keySet().toList(), unit.chapters.map { it.id })
            for (chapter in unit.chapters) {
                val row = json.getAsJsonObject(chapter.id)
                assertEquals(chapter.id, row.get("id").asString)
                assertEquals(2, chapter.a5Questions.size)
                for (index in 0..1) {
                    val q = chapter.a5Questions[index]
                    val expectedId = chapter.id + "-A5-" + (index + 1)
                    assertEquals(expectedId, q.id)
                    assertTrue("Repeated question ID " + expectedId, sourceIds.add(expectedId))
                    assertEquals(chapter.id, q.chapterId)
                    assertEquals(row.getAsJsonArray("q5_en")[index].asString, q.question.english)
                    assertEquals(row.getAsJsonArray("q5_ta")[index].asString, q.question.tamil)
                    assertEquals(5, q.answerPoints.size)
                    for (point in 0..4) {
                        assertEquals(
                            row.getAsJsonArray("a5_en")[index].asJsonArray[point].asString,
                            q.answerPoints[point].english,
                        )
                        assertEquals(
                            row.getAsJsonArray("a5_ta")[index].asJsonArray[point].asString,
                            q.answerPoints[point].tamil,
                        )
                    }
                }
            }
            assertEquals(ValidatedCorpusContract.a5PairsPerUnit.getValue(unit.number),
                unit.chapters.sumOf { it.a5Questions.size })
        }
        assertEquals(43, units.sumOf { it.chapters.size })
        assertEquals(86, sourceIds.size)
        assertEquals(sourceIds, parsedIds.toSet())
    }

    @Test
    fun everyDerivedQuestionIdMatchesAnIndependentlyCommittedValidationCsvRow() {
        val parsedIds = ValidatedA5Parser.parse(sources())
            .flatMap { it.chapters }.flatMap { it.a5Questions }.map { it.id }.toSet()
        val csvIds = mutableListOf<String>()
        for (unit in 1..5) {
            val validation = File(originalDir, "U" + unit + "_A5_VALIDATION.csv")
            assertTrue(validation.isFile)
            val found = idPattern.findAll(validation.readText(Charsets.UTF_8)).map { it.value }.toList()
            assertEquals("CSV row count for U" + unit,
                ValidatedCorpusContract.a5PairsPerUnit.getValue(unit), found.size)
            assertTrue(found.all { it.startsWith("u" + unit + "-") })
            csvIds.addAll(found)
        }
        assertEquals(86, csvIds.size)
        assertEquals(86, csvIds.toSet().size)
        assertEquals(csvIds.toSet(), parsedIds)
    }

    @Test
    fun corruptedIdentityAndAnswerStructureAreRejected() {
        val badId = sources().toMutableMap()
        val obj = JsonParser.parseString(badId.getValue(1)).asJsonObject
        val first = obj.entrySet().first()
        first.value.asJsonObject.addProperty("id", "wrong-chapter")
        badId[1] = obj.toString()
        assertThrows(IllegalArgumentException::class.java) { ValidatedA5Parser.parse(badId) }

        val missingPoint = sources().toMutableMap()
        val other = JsonParser.parseString(missingPoint.getValue(2)).asJsonObject
        other.entrySet().first().value.asJsonObject
            .getAsJsonArray("a5_ta")[0].asJsonArray.remove(4)
        missingPoint[2] = other.toString()
        assertThrows(IllegalArgumentException::class.java) { ValidatedA5Parser.parse(missingPoint) }

        assertThrows(IllegalArgumentException::class.java) {
            ValidatedA5Parser.parse(sources() - 3)
        }
    }

    @Test
    fun corpusContainsNoBlankQuestionOrAnswer() {
        val questions = ValidatedA5Parser.parse(sources())
            .flatMap { it.chapters }.flatMap { it.a5Questions }
        assertFalse(questions.isEmpty())
        for (q in questions) {
            assertTrue(q.question.english.isNotBlank() && q.question.tamil.isNotBlank())
            assertTrue(q.answerPoints.all { it.english.isNotBlank() && it.tamil.isNotBlank() })
        }
    }
}
