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

    /**
     * The v1.9.3 recovery files are immutable reference data, not editable lesson content.
     * QA Tamil editorial edits are strictly limited to three named field types.
     * Keep the frozen archives untouched; require exact digests for the candidate edits.
     */
    @Test
    fun packagedA5PreservesFrozenSourceExceptTamilEditorialCandidate() {
        val allowedFields = setOf("q_ta", "q2_ta", "a2_ta")
        val expectedDigest = mapOf(
            2 to "954b15c51f1e29d3ae28d2c3e7cc23ee35cea2c653b3cfe4368c7a1a0cbe42fd",
            3 to "d4d8ac1b1cd60ca9c1d0c0ab7b101adb961165d868a938112b8d6e52c208bc0a",
            4 to "541924877aaceb99ae0d7da4db0fe14dd8f6fea26902828c134637f8805bbdb1",
            5 to "76798c2170c3e78bffe72c2f802b70a5ab9df56bf6132677a13194d12a7e7fdb",
        )
        for (unit in 1..5) {
            val originalFile = original(unit)
            val packagedFile = bundled(unit)
            assertTrue("Missing frozen A5 source for U" + unit, originalFile.isFile)
            assertTrue("Missing packaged A5 source for U" + unit, packagedFile.isFile)
            if (unit == 1) {
                assertTrue(
                    "Unit I must remain byte-for-byte identical to frozen recovery",
                    originalFile.readBytes().contentEquals(packagedFile.readBytes()),
                )
                continue
            }
            val frozen = JsonParser.parseString(originalFile.readText(Charsets.UTF_8)).asJsonObject
            val candidate = JsonParser.parseString(packagedFile.readText(Charsets.UTF_8)).asJsonObject
            assertEquals("Chapter IDs changed in U" + unit, frozen.keySet(), candidate.keySet())
            var editorialFields = 0
            for (id in frozen.keySet()) {
                val prior = frozen.getAsJsonObject(id)
                val current = candidate.getAsJsonObject(id)
                assertEquals("Field set changed for " + id, prior.keySet(), current.keySet())
                for (key in prior.keySet()) {
                    if (key in allowedFields) {
                        assertFalse(
                            "Expected an explicit editorial replacement in " + id + "." + key,
                            prior.get(key) == current.get(key),
                        )
                        editorialFields++
                    } else {
                        assertEquals(
                            "Non-editorial field altered in " + id + "." + key,
                            prior.get(key), current.get(key),
                        )
                    }
                }
            }
            assertEquals("Wrong number of edited fields in U" + unit,
                expectedChapters[unit - 1] * 3, editorialFields)
            val digest = java.security.MessageDigest.getInstance("SHA-256")
                .digest(packagedFile.readBytes())
                .joinToString("") { "%02x".format(it.toInt() and 0xff) }
            assertEquals("Tamil editorial candidate bytes changed without review for U" + unit,
                expectedDigest.getValue(unit), digest)
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
