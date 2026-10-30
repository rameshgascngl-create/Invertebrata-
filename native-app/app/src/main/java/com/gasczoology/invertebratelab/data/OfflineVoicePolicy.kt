package com.gasczoology.invertebratelab.data

/** Android-free eligibility policy; initialization alone proves no audio acceptance. */
data class OfflineVoiceCandidate(
    val name: String, val language: String, val country: String,
    val requiresNetwork: Boolean, val missingData: Boolean,
)
object OfflineVoicePolicy {
    fun choose(voices: List<OfflineVoiceCandidate>, language: AppLanguage): OfflineVoiceCandidate? {
        val code = if (language == AppLanguage.TAMIL) "ta" else "en"
        return voices.filter { it.language == code && !it.requiresNetwork && !it.missingData }
            .sortedWith(compareBy<OfflineVoiceCandidate> { it.country != "IN" }.thenBy { it.name })
            .firstOrNull()
    }
}

enum class NarrationPhase { INITIALIZING, READY, QUEUED, PLAYING, COMPLETE, STOPPED, UNAVAILABLE, ERROR }

/** Reject callbacks from flushed/stopped utterances, including a language change. */
class NarrationSession {
    var phase = NarrationPhase.INITIALIZING
        private set
    private var serial = 0L
    private var activeId: String? = null
    fun ready() { if (phase == NarrationPhase.INITIALIZING) phase = NarrationPhase.READY }
    fun queue(): String { activeId = "native-narration-${++serial}"; phase = NarrationPhase.QUEUED; return activeId!! }
    fun start(id: String) { if (activeId == id) phase = NarrationPhase.PLAYING }
    fun complete(id: String) { if (activeId == id) { phase = NarrationPhase.COMPLETE; activeId = null } }
    fun error(id: String? = null) { if (id == null || id == activeId) { phase = NarrationPhase.ERROR; activeId = null } }
    fun unavailable() { activeId = null; phase = NarrationPhase.UNAVAILABLE }
    fun stop() { activeId = null; phase = NarrationPhase.STOPPED }
}
