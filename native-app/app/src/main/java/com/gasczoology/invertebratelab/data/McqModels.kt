package com.gasczoology.invertebratelab.data

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

/**
 * One four-option question with a stored answer key. The key is [correctIndex]; nothing infers
 * correctness from option position, option text or shuffling.
 */
data class McqQuestion(
    val id: String,
    val chapterId: String,
    val stem: BilingualText,
    val options: List<BilingualText>,
    val correctIndex: Int,
    val explanation: BilingualText,
) {
    init {
        require(id.isNotBlank() && chapterId.isNotBlank()) { "MCQ id and chapter id are required" }
        require(options.size == OPTION_COUNT) { "$id: exactly $OPTION_COUNT options required, found ${options.size}" }
        require(correctIndex in 0 until OPTION_COUNT) { "$id: answer index $correctIndex is outside the options" }
        require(stem.english.isNotBlank() && stem.tamil.isNotBlank()) { "$id: stem needs English and Tamil" }
        require(options.all { it.english.isNotBlank() && it.tamil.isNotBlank() }) { "$id: every option needs English and Tamil" }
        require(options.map { it.english.trim().lowercase() }.toSet().size == OPTION_COUNT) { "$id: duplicate options" }
        require(explanation.english.isNotBlank() && explanation.tamil.isNotBlank()) { "$id: explanation needs English and Tamil" }
    }
    companion object { const val OPTION_COUNT = 4 }
}

/** [verified] is only true when the file itself declares status VALIDATED; anything else is shown as a draft. */
data class McqBank(val questions: List<McqQuestion>, val verified: Boolean) {
    fun forChapter(chapterId: String) = questions.filter { it.chapterId == chapterId }
    fun forUnit(unit: Int) = questions.filter { runCatching { BookLayout.unitNumber(it.chapterId) }.getOrNull() == unit }

    companion object {
        val EMPTY = McqBank(emptyList(), false)

        /**
         * Strict: any malformed question rejects the whole file, so a half-valid bank can never be
         * shown. Format: {"schema":1,"status":"VALIDATED"|"DRAFT","questions":[{"id","chapterId",
         * "stem":{"en","ta"},"options":[{"en","ta"} x4],"answer":0-3,"explanation":{"en","ta"}}]}
         */
        fun parse(json: String): McqBank {
            val root = JsonParser.parseString(json).asJsonObject
            require(root.get("schema")?.asInt == 1) { "Unsupported MCQ schema" }
            val status = root.get("status")?.asString.orEmpty()
            require(status == "VALIDATED" || status == "DRAFT") { "MCQ status must be VALIDATED or DRAFT" }
            fun text(o: JsonObject, where: String) = BilingualText(
                o.get("en")?.asString ?: error("$where: missing en"),
                o.get("ta")?.asString ?: error("$where: missing ta"))
            val questions = root.getAsJsonArray("questions").map { element ->
                val q = element.asJsonObject
                val id = q.get("id")?.asString.orEmpty()
                McqQuestion(
                    id = id,
                    chapterId = q.get("chapterId")?.asString.orEmpty(),
                    stem = text(q.getAsJsonObject("stem"), "$id stem"),
                    options = q.getAsJsonArray("options").mapIndexed { i, o -> text(o.asJsonObject, "$id option $i") },
                    correctIndex = q.get("answer")?.asInt ?: error("$id: missing answer"),
                    explanation = text(q.getAsJsonObject("explanation"), "$id explanation"),
                )
            }
            require(questions.map { it.id }.toSet().size == questions.size) { "Duplicate MCQ ids" }
            return McqBank(questions, status == "VALIDATED")
        }
    }
}

/**
 * The state of one practice run. Immutable and pure; the UI only renders it and calls these functions.
 * Persisted as primitives (ids, index, selection, answers) so it survives process death.
 */
data class McqSession(
    val questions: List<McqQuestion>,
    val index: Int = 0,
    val selected: Int? = null,
    val submitted: Boolean = false,
    /** Submitted answers by question id; a question is answered at most once per run. */
    val answers: Map<String, Int> = emptyMap(),
) {
    val current: McqQuestion? get() = questions.getOrNull(index)
    val finished: Boolean get() = questions.isNotEmpty() && index >= questions.size
    val score: Int get() = questions.count { answers[it.id] == it.correctIndex }
    val incorrect: List<McqQuestion> get() = questions.filter { it.id in answers && answers[it.id] != it.correctIndex }
    val currentIsCorrect: Boolean get() = current?.let { submitted && selected == it.correctIndex } ?: false

    fun select(option: Int): McqSession =
        if (submitted || current == null || option !in 0 until McqQuestion.OPTION_COUNT) this else copy(selected = option)

    fun submit(): McqSession {
        val q = current ?: return this
        val choice = selected ?: return this
        return if (submitted) this else copy(submitted = true, answers = answers + (q.id to choice))
    }

    fun next(): McqSession = if (!submitted || finished) this else copy(index = index + 1, selected = null, submitted = false)

    /** A new run over only the questions answered wrongly, or null if there are none. */
    fun retryIncorrect(): McqSession? = incorrect.takeIf { it.isNotEmpty() }?.let { McqSession(it) }

    fun encode(): String = Gson().toJson(Saved(questions.map { it.id }, index, selected ?: -1, submitted, answers))

    private data class Saved(val ids: List<String>, val index: Int, val selected: Int, val submitted: Boolean, val answers: Map<String, Int>)

    companion object {
        /** Null if the text is corrupt or names a question the bank no longer has; the caller then starts afresh. */
        fun decode(raw: String?, bank: List<McqQuestion>): McqSession? = runCatching {
            val s = Gson().fromJson(raw, Saved::class.java) ?: return null
            val byId = bank.associateBy { it.id }
            val qs = s.ids.map { byId[it] ?: return null }
            if (qs.isEmpty() || s.index !in 0..qs.size) return null
            if (s.selected !in -1 until McqQuestion.OPTION_COUNT) return null
            if (s.answers.any { (id, a) -> id !in s.ids || a !in 0 until McqQuestion.OPTION_COUNT }) return null
            McqSession(qs, s.index, s.selected.takeIf { it >= 0 }, s.submitted, s.answers)
        }.getOrNull()
    }
}
