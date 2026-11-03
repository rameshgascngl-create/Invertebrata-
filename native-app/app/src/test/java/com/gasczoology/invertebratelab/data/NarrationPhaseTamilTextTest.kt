package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class NarrationPhaseTamilTextTest {
    @Test fun everyVoiceStateHasTamilTextWithoutLeakingItsEnumName() {
        NarrationPhase.values().forEach { phase ->
            val text = phase.displayText(AppLanguage.TAMIL)
            assertTrue(text.any { it in '\u0B80'..'\u0BFF' })
            assertFalse(text.contains(phase.name))
        }
        assertEquals("நிறுத்தப்பட்டுள்ளது", NarrationPhase.STOPPED.displayText(AppLanguage.TAMIL))
        assertEquals("ஒலிக்கிறது", NarrationPhase.PLAYING.displayText(AppLanguage.TAMIL))
    }
    @Test fun displayTranslationDoesNotChangeEnglishStatusOrStoredIdentity() {
        NarrationPhase.values().forEach { phase ->
            assertEquals(phase.name, phase.displayText(AppLanguage.ENGLISH))
            assertEquals(phase, NarrationPhase.valueOf(phase.name))
        }
    }
}
