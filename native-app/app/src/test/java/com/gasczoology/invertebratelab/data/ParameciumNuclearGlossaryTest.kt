package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ParameciumNuclearGlossaryTest {
    @Test fun definitionsAreBilingual() {
        val terms = ParameciumNuclearGlossary.fission + ParameciumNuclearGlossary.conjugation
        assertEquals(6, terms.size)
        assertTrue(terms.all { it.name.english.isNotBlank() && it.name.tamil.isNotBlank() && it.definition.english.isNotBlank() && it.definition.tamil.isNotBlank() })
    }
}
