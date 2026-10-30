package com.gasczoology.invertebratelab.data

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser

/**
 * Strict, lossless mapping from the five accepted A5 JSON documents.
 * Never paraphrase or normalise question or answer content.
 * The stable question IDs are independently cross-checked against the committed
 * U1–U5 A5 validation CSVs in ValidatedA5ParserTest.
 */
object ValidatedA5Parser {
    fun parse(sourceByUnit: Map<Int, String>): List<AcademicUnit> {
        require(sourceByUnit.keys == (1..5).toSet()) {
            "Expected exactly five numbered unit JSON documents"
        }
        val allChapterIds = mutableSetOf<String>()
        val allQuestionIds = mutableSetOf<String>()
        val units = (1..5).map { number ->
            val root = JsonParser.parseString(sourceByUnit.getValue(number))
            require(root.isJsonObject) { "Unit " + number + " must be a JSON object" }
            val chapterRecords = root.asJsonObject
            val chapters = chapterRecords.entrySet().map { (chapterId, element) ->
                require(allChapterIds.add(chapterId)) { "Duplicate chapter: " + chapterId }
                require(chapterId.startsWith("u" + number + "-")) {
                    "Chapter belongs to wrong unit: " + chapterId
                }
                require(element.isJsonObject) { "Chapter is not an object: " + chapterId }
                val record = element.asJsonObject
                require(readString(record.get("id"), chapterId + ".id") == chapterId) {
                    "Chapter identity differs from JSON key: " + chapterId
                }
                val questionsEnglish = requiredPair(record, "q5_en", chapterId)
                val questionsTamil = requiredPair(record, "q5_ta", chapterId)
                val answersEnglish = requiredPair(record, "a5_en", chapterId)
                val answersTamil = requiredPair(record, "a5_ta", chapterId)
                val questions = (0..1).map { index ->
                    val questionId = chapterId + "-A5-" + (index + 1)
                    require(allQuestionIds.add(questionId)) {
                        "Duplicate five-mark question ID: " + questionId
                    }
                    val enPoints = requiredFivePoints(answersEnglish[index], questionId, "a5_en")
                    val taPoints = requiredFivePoints(answersTamil[index], questionId, "a5_ta")
                    A5Question(
                        id = questionId,
                        chapterId = chapterId,
                        question = BilingualText(
                            english = readString(questionsEnglish[index], questionId + ".q5_en"),
                            tamil = readString(questionsTamil[index], questionId + ".q5_ta"),
                        ),
                        answerPoints = (0..4).map { pointIndex ->
                            BilingualText(enPoints[pointIndex], taPoints[pointIndex])
                        },
                    )
                }
                Chapter(
                    id = chapterId,
                    unitNumber = number,
                    // The A5 corpus has no chapter-title field. Do not invent translated titles.
                    title = BilingualText(chapterId, chapterId),
                    a5Questions = questions,
                )
            }
            require(chapters.size == ValidatedCorpusContract.a5PairsPerUnit.getValue(number) / 2) {
                "Unexpected number of chapters in unit " + number
            }
            AcademicUnit(
                number = number,
                title = BilingualText("Unit " + number, "அலகு " + number),
                chapters = chapters,
            )
        }
        ValidatedCorpusContract.validate(units)
        return units
    }

    private fun requiredPair(record: JsonObject, field: String, chapterId: String): JsonArray {
        val element = record.get(field)
        require(element != null && element.isJsonArray && element.asJsonArray.size() == 2) {
            chapterId + "." + field + " must contain exactly two questions or answers"
        }
        return element.asJsonArray
    }

    private fun requiredFivePoints(item: JsonElement, id: String, field: String): List<String> {
        require(item.isJsonArray && item.asJsonArray.size() == 5) {
            id + "." + field + " must contain exactly five points"
        }
        return item.asJsonArray.mapIndexed { index, point ->
            readString(point, id + "." + field + "[" + index + "]")
        }
    }

    private fun readString(item: JsonElement?, context: String): String {
        require(item != null && item.isJsonPrimitive && item.asJsonPrimitive.isString) {
            "Missing or non-string bilingual text: " + context
        }
        val value = item.asString
        require(value.isNotBlank()) { "Empty bilingual text: " + context }
        return value // Exact source Unicode and punctuation are retained.
    }
}
