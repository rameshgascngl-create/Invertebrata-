package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class McqSessionTest {
    private fun t(s: String) = BilingualText(s, "த$s")
    private fun q(id: String, answer: Int, chapter: String = "u1-paramecium") = McqQuestion(
        id, chapter, t("stem $id"), listOf(t("a$id"), t("b$id"), t("c$id"), t("d$id")), answer, t("because $id"))
    private val bank = listOf(q("q1", 0), q("q2", 3), q("q3", 2))

    @Test fun correctnessUsesTheStoredKeyNotPosition() {
        var s = McqSession(bank).select(0).submit()
        assertTrue(s.currentIsCorrect)                       // q1 key is option 0
        s = s.next().select(0).submit()
        assertFalse(s.currentIsCorrect)                      // q2 key is option 3, so option 0 is wrong
        s = s.next().select(2).submit().next()
        assertEquals(2, s.score)
        assertEquals(listOf("q2"), s.incorrect.map { it.id })
        assertTrue(s.finished)
    }

    @Test fun cannotSubmitWithoutSelectionChangeAfterSubmitOrSkipAhead() {
        val s = McqSession(bank)
        assertSame(s, s.submit())
        assertSame(s, s.next())
        val done = s.select(1).submit()
        assertSame("selection locked after submit", done, done.select(2))
        assertEquals(done, done.submit())
        assertEquals(mapOf("q1" to 1), done.answers)
        assertSame(s, s.select(4)); assertSame(s, s.select(-1))
    }

    @Test fun retryIncorrectRunsOnlyWrongQuestionsAndIsNullWhenPerfect() {
        var s = McqSession(bank)
        listOf(0, 0, 2).forEach { s = s.select(it).submit().next() }
        val retry = s.retryIncorrect()!!
        assertEquals(listOf("q2"), retry.questions.map { it.id })
        assertEquals(0, retry.score); assertEquals(0, retry.index); assertTrue(retry.answers.isEmpty())
        var perfect = McqSession(bank)
        listOf(0, 3, 2).forEach { perfect = perfect.select(it).submit().next() }
        assertEquals(3, perfect.score); assertNull(perfect.retryIncorrect())
    }

    @Test fun emptyBankIsNeverFinishedAndHasNoCurrentQuestion() {
        val s = McqSession(emptyList())
        assertNull(s.current); assertFalse(s.finished); assertEquals(0, s.score)
        assertSame(s, s.select(0)); assertSame(s, s.submit()); assertNull(s.retryIncorrect())
    }

    @Test fun stateRoundTripsThroughProcessDeathAndRejectsStaleOrCorruptState() {
        val s = McqSession(bank).select(0).submit().next().select(1)
        assertEquals(s, McqSession.decode(s.encode(), bank))
        assertNull("question removed from the bank", McqSession.decode(s.encode(), bank.drop(1)))
        listOf(null, "", "garbage", "{}", "{\"ids\":[],\"index\":0,\"selected\":-1,\"submitted\":false,\"answers\":{}}",
            "{\"ids\":[\"q1\"],\"index\":9,\"selected\":-1,\"submitted\":false,\"answers\":{}}",
            "{\"ids\":[\"q1\"],\"index\":0,\"selected\":7,\"submitted\":false,\"answers\":{}}",
            "{\"ids\":[\"q1\"],\"index\":0,\"selected\":-1,\"submitted\":false,\"answers\":{\"zz\":1}}").forEach {
            assertNull("'$it'", McqSession.decode(it, bank))
        }
    }

    @Test fun languageIsOnlyAViewOfTheSameState() {
        val s = McqSession(bank).select(2)
        assertEquals("stem q1", s.current!!.stem.value(AppLanguage.ENGLISH))
        assertEquals("தstem q1", s.current!!.stem.value(AppLanguage.TAMIL))
        assertEquals(2, s.selected)
    }

    @Test fun invalidQuestionsAreRejected() {
        fun build(options: List<BilingualText> = bank[0].options, answer: Int = 0, stem: BilingualText = t("s")) =
            McqQuestion("x", "u1-paramecium", stem, options, answer, t("e"))
        assertThrows(IllegalArgumentException::class.java) { build(options = bank[0].options.take(3)) }
        assertThrows(IllegalArgumentException::class.java) { build(answer = 4) }
        assertThrows(IllegalArgumentException::class.java) { build(answer = -1) }
        assertThrows(IllegalArgumentException::class.java) { build(options = listOf(t("a"), t("a"), t("c"), t("d"))) }
        assertThrows(IllegalArgumentException::class.java) { build(stem = BilingualText("s", "")) }
    }

    private fun json(status: String = "DRAFT", answer: Int = 1, extra: String = "") = """
        {"schema":1,"status":"$status","questions":[{"id":"m1","chapterId":"u1-paramecium",
        "stem":{"en":"S","ta":"த"},"options":[{"en":"a","ta":"அ"},{"en":"b","ta":"ஆ"},{"en":"c","ta":"இ"},{"en":"d","ta":"ஈ"}],
        "answer":$answer,"explanation":{"en":"E","ta":"வி"}}$extra]}"""

    @Test fun bankParsingIsStrictAndOnlyTrustsDeclaredStatus() {
        val draft = McqBank.parse(json("DRAFT"))
        assertFalse(draft.verified); assertEquals(1, draft.forChapter("u1-paramecium").size); assertEquals(1, draft.forUnit(1).size)
        assertTrue(McqBank.parse(json("VALIDATED")).verified)
        assertThrows(IllegalArgumentException::class.java) { McqBank.parse(json("APPROVED")) }
        assertThrows(IllegalArgumentException::class.java) { McqBank.parse(json(answer = 9)) }
        assertThrows(IllegalArgumentException::class.java) { McqBank.parse(json().replace("\"schema\":1", "\"schema\":2")) }
        val dup = ""","""+json().substringAfter("\"questions\":[").substringBeforeLast("]}")
        assertThrows(IllegalArgumentException::class.java) { McqBank.parse(json(extra = dup)) }
        assertEquals(0, McqBank.EMPTY.questions.size)
    }

    /** The repository ships no questions until a reviewed bank is supplied; none may be invented. */
    @Test fun shippedAssetsContainOnlyValidBanksOrNone() {
        val dir = java.io.File("src/main/assets/mcq")
        dir.listFiles { f -> f.extension == "json" }.orEmpty().forEach { McqBank.parse(it.readText()) }
    }
}
