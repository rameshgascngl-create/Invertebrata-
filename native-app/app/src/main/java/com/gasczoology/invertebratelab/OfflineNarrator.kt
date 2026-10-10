package com.gasczoology.invertebratelab

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.BilingualText
import com.gasczoology.invertebratelab.data.NarrationPhase
import com.gasczoology.invertebratelab.data.NarrationSession
import com.gasczoology.invertebratelab.data.OfflineVoiceCandidate
import com.gasczoology.invertebratelab.data.OfflineVoicePolicy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Native Android TTS. Uses only advertised installed embedded voices. */
class OfflineNarrator(context: Context) {
    private val main = Handler(Looper.getMainLooper())
    private val session = NarrationSession()
    private val _phase = MutableStateFlow(NarrationPhase.INITIALIZING)
    val phase = _phase.asStateFlow()
    private val _script = MutableStateFlow<BilingualText?>(null)
    val script = _script.asStateFlow()
    private var initialized = false
    private var closed = false
    private var engine: TextToSpeech? = null
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val attributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build()
    private val focusListener = AudioManager.OnAudioFocusChangeListener { change ->
        if (change == AudioManager.AUDIOFOCUS_LOSS || change == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
            change == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) main.post { stop() }
    }
    private var focusRequest: AudioFocusRequest? = null
    init {
        engine = TextToSpeech(context.applicationContext) { result -> main.post {
            if (closed) return@post
            initialized = result == TextToSpeech.SUCCESS
            if (initialized) {
                engine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(id: String) = dispatch { session.start(id) }
                    override fun onDone(id: String) = dispatch {
                        session.complete(id)
                        if (session.phase == NarrationPhase.COMPLETE) releaseFocus()
                    }
                    @Deprecated("Platform callback")
                    override fun onError(id: String) = dispatch { fail(id) }
                    override fun onError(id: String, errorCode: Int) = dispatch { fail(id) }
                })
                session.ready()
            } else session.unavailable()
            publish()
        } }
    }
    private fun dispatch(action: () -> Unit) { main.post { if (!closed) { action(); publish() } } }
    private fun fail(id: String) {
        session.error(id)
        if (session.phase == NarrationPhase.ERROR) releaseFocus()
    }
    @Suppress("DEPRECATION")
    private fun acquireFocus(): Boolean {
        val result = if (Build.VERSION.SDK_INT >= 26) {
            val request = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                .setAudioAttributes(attributes).setWillPauseWhenDucked(true)
                .setOnAudioFocusChangeListener(focusListener, main).build()
            focusRequest = request
            audioManager.requestAudioFocus(request)
        } else audioManager.requestAudioFocus(focusListener, AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
    }
    @Suppress("DEPRECATION")
    private fun releaseFocus() {
        if (Build.VERSION.SDK_INT >= 26) focusRequest?.let { audioManager.abandonAudioFocusRequest(it) }
        else audioManager.abandonAudioFocus(focusListener)
        focusRequest = null
    }
    private fun publish() { _phase.value = session.phase; Log.i("R161_AUDIO", "phase=${session.phase}") }
    fun speak(script: BilingualText, language: AppLanguage) {
        _script.value = script // Complete written fallback always retained.
        if (!initialized || closed) { session.unavailable(); publish(); return }
        val tts = engine ?: return
        session.stop() // Invalidate the previous utterance BEFORE QUEUE_FLUSH.
        tts.stop()
        releaseFocus()
        val voices = tts.voices.orEmpty()
        val chosen = OfflineVoicePolicy.choose(voices.map {
            OfflineVoiceCandidate(it.name, it.locale.language, it.locale.country,
                it.isNetworkConnectionRequired,
                it.features?.contains(TextToSpeech.Engine.KEY_FEATURE_NOT_INSTALLED) == true)
        }, language)
        if (chosen == null) { session.unavailable(); publish(); return }
        val voice = voices.first { it.name == chosen.name }
        if (tts.setVoice(voice) != TextToSpeech.SUCCESS) { session.error(); publish(); return }
        if (tts.setAudioAttributes(attributes) != TextToSpeech.SUCCESS || !acquireFocus()) {
            session.error(); releaseFocus(); publish(); return
        }
        val id = session.queue()
        publish() // Only QUEUED here, never claim PLAYING before callback.
        Log.i("R161_AUDIO", "voice=${voice.name} locale=${voice.locale} network=${voice.isNetworkConnectionRequired} id=$id")
        if (tts.speak(script.value(language), TextToSpeech.QUEUE_FLUSH, null, id) != TextToSpeech.SUCCESS) {
            fail(id); publish()
        }
    }
    fun stop() { session.stop(); engine?.stop(); releaseFocus(); publish() }
    fun close() { if (!closed) { stop(); closed = true; engine?.shutdown() } }
}
