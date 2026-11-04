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
     * QA Tamil edits are limited to three original field types and 38 indexed steps_ta replacements; one U4 steps_en scientific correction is separately pinned.
     * Keep the frozen archives untouched; require exact digests for the candidate edits.
     */
    @Test
    fun packagedA5PreservesFrozenSourceExceptTamilEditorialCandidate() {
        val allowedFields = setOf("q_ta", "q2_ta", "a2_ta")
        val expectedDigest = mapOf(
            2 to "88ecdc7ebf7185ecfdb46e7a14d1fd5c2c04071cf1cb23acfe987a8e6c93d76a",
            3 to "eba6cfaef87dd3be5d0251b717bafd4bf894db48659ccb4c04231d2362c665be",
            4 to "2bc16aeb947c4e897b5af7edf662ab330824323e0319cf6c904eb7b2b8a2f9f2",
            5 to "38c6e9ef0e330b76603c011a82b60cd03e609706c03c0233c87b688cfbebfd96",
        )
        val expectedStepIndices = mapOf(
            2 to mapOf(
                "u2-sycon" to setOf(1),
                "u2-corals" to setOf(2),
                "u2-coral-economics" to setOf(2),
                "u2-porifera" to setOf(2),
                "u2-obelia" to setOf(0),
                "u2-cnidaria" to setOf(0),
                "u2-reproduction" to setOf(0),
            ),
            3 to mapOf(
                "u3-fasciola" to setOf(5),
                "u3-ascaris" to setOf(4),
                "u3-platy" to setOf(3),
                "u3-nematode-parasites" to setOf(0),
                "u3-aschel" to setOf(0),
            ),
            4 to mapOf(
                "u4-arthropoda" to setOf(1),
                "u4-penaeus" to setOf(1),
                "u4-modes-life" to setOf(0),
                "u4-metamerism" to setOf(1),
            ),
            5 to mapOf(
                "u5-mollusca" to setOf(1),
                "u5-echinodermata" to setOf(0, 2),
                "u5-cephalopods" to setOf(1),
                "u5-pearl" to setOf(2),
                "u5-echino-affinities" to setOf(4),
            ),
        )
        // Phase III scientific corrections: index values are zero-based; previous 22 remain intact.
        val phase3TamilSteps = mapOf(
            2 to mapOf("u2-sycon" to setOf(2), "u2-canals" to setOf(1, 2), "u2-obelia" to setOf(2, 3)),
            3 to mapOf("u3-fasciola" to setOf(1, 3), "u3-ascaris" to setOf(3)),
            4 to mapOf("u4-modes-life" to setOf(1), "u4-crustacean-larvae" to setOf(1), "u4-penaeus" to setOf(4)),
            5 to mapOf("u5-echino-larvae" to setOf(3), "u5-echinodermata" to setOf(4),
                "u5-asterias" to setOf(3), "u5-torsion" to setOf(0), "u5-pearl" to setOf(1)),
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
            var stepReplacements = 0
            val baselineIndices = expectedStepIndices[unit].orEmpty()
            val phase3Indices = phase3TamilSteps[unit].orEmpty()
            val permitted = (baselineIndices.keys + phase3Indices.keys).associateWith { id ->
                baselineIndices[id].orEmpty() + phase3Indices[id].orEmpty()
            }
            for (id in frozen.keySet()) {
                val prior = frozen.getAsJsonObject(id)
                val current = candidate.getAsJsonObject(id)
                assertEquals("Field set changed for " + id, prior.keySet(), current.keySet())
                for (key in prior.keySet()) {
                    when {
                        key in allowedFields -> {
                            assertFalse(
                                "Expected an explicit editorial replacement in " + id + "." + key,
                                prior.get(key) == current.get(key),
                            )
                            editorialFields++
                        }
                        key == "steps_en" -> {
                            val before = prior.getAsJsonArray(key)
                            val after = current.getAsJsonArray(key)
                            assertEquals("English step count changed in " + id, before.size(), after.size())
                            for (index in 0 until before.size()) {
                                if (unit == 4 && id == "u4-modes-life" && index == 1) {
                                    assertEquals("Correct the scientifically misleading leech-coelom statement",
                                        "Coelomic hydrostatic support varies; the leech coelom is greatly reduced.",
                                        after[index].asString)
                                    assertFalse("U4 coelom correction must change frozen shorthand", before[index] == after[index])
                                } else {
                                    assertEquals("Unapproved English steps_en change in " + id + "[" + index + "]",
                                        before[index], after[index])
                                }
                            }
                        }
                        key == "steps_ta" -> {
                            val before = prior.getAsJsonArray(key)
                            val after = current.getAsJsonArray(key)
                            assertEquals("Step count changed in " + id, before.size(), after.size())
                            val approvedIndices = permitted[id].orEmpty()
                            assertTrue("Out-of-range step index in " + id,
                                approvedIndices.all { it >= 0 && it < before.size() })
                            for (index in 0 until before.size()) {
                                if (index in approvedIndices) {
                                    assertFalse("Expected Tamil steps_ta replacement for " + id + "[" + index + "]",
                                        before[index] == after[index])
                                    stepReplacements++
                                } else {
                                    assertEquals("Unapproved change to steps_ta in " + id + "[" + index + "]",
                                        before[index], after[index])
                                }
                            }
                        }
                        else -> assertEquals(
                            "Non-editorial field altered in " + id + "." + key,
                            prior.get(key), current.get(key),
                        )
                    }
                }
            }
            assertEquals("Wrong number of edited fields in U" + unit,
                expectedChapters[unit - 1] * 3, editorialFields)
            assertEquals("Wrong number of revised Tamil step items in U" + unit,
                permitted.values.sumOf { it.size }, stepReplacements)
            val digest = java.security.MessageDigest.getInstance("SHA-256")
                .digest(packagedFile.readBytes())
                .joinToString("") { "%02x".format(it.toInt() and 0xff) }
            assertEquals("Tamil editorial candidate bytes changed without review for U" + unit,
                expectedDigest.getValue(unit), digest)
        }
    }

    @Test
    fun phase3TeachingCuesRetainCriticalScientificDistinctions() {
        val u2 = JsonParser.parseString(bundled(2).readText(Charsets.UTF_8)).asJsonObject
        val u3 = JsonParser.parseString(bundled(3).readText(Charsets.UTF_8)).asJsonObject
        val u4 = JsonParser.parseString(bundled(4).readText(Charsets.UTF_8)).asJsonObject
        val u5 = JsonParser.parseString(bundled(5).readText(Charsets.UTF_8)).asJsonObject
        assertTrue(u2.getAsJsonObject("u2-sycon").getAsJsonArray("steps_ta")[2].asString.contains("ஆரக் கால்வாய்களின்"))
        assertTrue(u3.getAsJsonObject("u3-ascaris").getAsJsonArray("steps_ta")[3].asString.contains("embryonated egg"))
        assertTrue(u4.getAsJsonObject("u4-modes-life").getAsJsonArray("steps_ta")[1].asString.contains("உடற்குழி பெரிதும் குறைந்துள்ளது"))
        assertTrue(u4.getAsJsonObject("u4-modes-life").getAsJsonArray("steps_en")[1].asString.contains("leech coelom is greatly reduced"))
        assertTrue(u4.getAsJsonObject("u4-crustacean-larvae").getAsJsonArray("steps_ta")[1].asString.contains("protozoea"))
        assertTrue(u5.getAsJsonObject("u5-echinodermata").getAsJsonArray("steps_ta")[4].asString.contains("Ambulacraria"))
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
