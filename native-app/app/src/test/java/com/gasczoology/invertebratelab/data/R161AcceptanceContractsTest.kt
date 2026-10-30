package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class R161AcceptanceContractsTest {
    private fun voice(name: String, language: String = "ta", country: String = "IN",
        network: Boolean = false, missing: Boolean = false) =
        OfflineVoiceCandidate(name, language, country, network, missing)

    @Test fun networkAndMissingVoicePackagesAreNeverEligibleOffline() {
        assertNull(OfflineVoicePolicy.choose(listOf(voice("cloud", network = true),
            voice("uninstalled", missing = true), voice("wrong-language", language = "en")), AppLanguage.TAMIL))
    }
    @Test fun languageSwitchUsesRequestedLanguageAndPrefersIndianLocalVoice() {
        val voices = listOf(voice("english", "en"), voice("tamil-sg", country = "SG"), voice("tamil-in"))
        assertEquals("english", OfflineVoicePolicy.choose(voices, AppLanguage.ENGLISH)?.name)
        assertEquals("tamil-in", OfflineVoicePolicy.choose(voices, AppLanguage.TAMIL)?.name)
    }
    @Test fun initializationAndQueueDoNotClaimPlayback() {
        val s = NarrationSession(); s.ready(); assertEquals(NarrationPhase.READY, s.phase)
        s.queue(); assertEquals(NarrationPhase.QUEUED, s.phase)
    }
    @Test fun replayIgnoresStaleCallbacksAndRequiresNewPlaybackCallback() {
        val s = NarrationSession(); val first = s.queue(); s.start(first)
        val replay = s.queue(); s.complete(first); s.error(first)
        assertEquals(NarrationPhase.QUEUED, s.phase)
        s.start(replay); assertEquals(NarrationPhase.PLAYING, s.phase)
        s.complete(replay); assertEquals(NarrationPhase.COMPLETE, s.phase)
    }
    @Test fun interruptionInvalidatesPendingUtterance() {
        val s = NarrationSession(); val id = s.queue(); s.start(id); s.stop(); s.complete(id); s.start(id)
        assertEquals(NarrationPhase.STOPPED, s.phase)
    }
    @Test fun missingVoiceAndSynthesisErrorRemainDistinct() {
        val s = NarrationSession(); s.unavailable(); assertEquals(NarrationPhase.UNAVAILABLE, s.phase)
        val id = s.queue(); s.error(id); assertEquals(NarrationPhase.ERROR, s.phase)
    }
    @Test fun basalBodyTripletsAreNotTheAxonemeCentralPair() {
        assertEquals(9, ParameciumCiliaryAcademicContent.BASAL_BODY_TRIPLETS)
        assertEquals(0, ParameciumCiliaryAcademicContent.BASAL_BODY_CENTRAL_PAIR)
        assertEquals(9, ParameciumCiliaryAcademicContent.PERIPHERAL_DOUBLETS)
        assertEquals(2, ParameciumCiliaryAcademicContent.CENTRAL_SINGLETS)
    }
    @Test fun completeBilingualLessonsRetainMicroscopyAndSpeciesLimits() {
        val notes = ParameciumCiliaryAcademicContent.subsections
        assertEquals(3, notes.size)
        notes.forEach { assertTrue(it.explanation.english.length > 500); assertTrue(it.explanation.tamil.length > 500) }
        val english = notes.joinToString { it.explanation.english }
        val tamil = notes.joinToString { it.explanation.tamil }
        assertTrue(english.contains("three-dimensional")); assertTrue(english.contains("electron microscopy"))
        assertTrue(english.contains("Paramecium tetraurelia")); assertTrue(english.contains("Paramecium caudatum"))
        assertTrue(tamil.contains("மும்மைகள்")); assertTrue(tamil.contains("முப்பரிமாண"))
        assertTrue(notes.last().explanation.english.contains("not every", ignoreCase = true))
    }
    @Test fun schemaOneDefaultsRemainBackwardCompatible() {
        val state = NativeLearningState(destination = StudyDestination.PRACTICE,
            chapterId = "u1-paramecium", questionId = "u1-paramecium-A5-1", answerRevealed = true)
        assertEquals(1, state.schemaVersion); assertEquals(0, state.ciliaryStageIndex)
        assertEquals("identity-and-habitat", state.textbookSectionId); assertEquals("study", state.laboratoryTab)
        assertTrue(state.answerRevealed)
    }
    @Test fun invalidNewFieldsDoNotDestroyValidExistingLabDestination() {
        val units = listOf(AcademicUnit(1, BilingualText("Unit", "அலகு"), listOf(
            Chapter("u1-paramecium", 1, BilingualText("Paramecium", "பாரமீசியம்"), emptyList()))))
        val corrected = NativeLearningState(destination = StudyDestination.PARAMECIUM_LAB,
            chapterId = "u1-paramecium", ciliaryStageIndex = 100, textbookSectionId = "unknown",
            laboratoryTab = "unknown").validatedAgainst(units)
        assertEquals(StudyDestination.PARAMECIUM_LAB, corrected.destination)
        assertEquals(0, corrected.ciliaryStageIndex); assertEquals("identity-and-habitat", corrected.textbookSectionId)
        assertEquals("study", corrected.laboratoryTab)
    }
}
