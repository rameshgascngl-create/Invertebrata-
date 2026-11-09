package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class BookmarksTest {
    @Test fun toggleAddsThenRemovesAndDropsEmptyChapters() {
        val one = Bookmarks.toggle(emptyMap(), "u1-paramecium", "row-3")
        assertEquals(mapOf("u1-paramecium" to setOf("row-3")), one)
        val two = Bookmarks.toggle(one, "u1-paramecium", "summary")
        assertEquals(setOf("row-3", "summary"), two.getValue("u1-paramecium"))
        val back = Bookmarks.toggle(Bookmarks.toggle(two, "u1-paramecium", "row-3"), "u1-paramecium", "summary")
        assertTrue(back.isEmpty())
    }

    @Test fun chaptersAreIndependent() {
        val m = Bookmarks.toggle(Bookmarks.toggle(emptyMap(), "a", "row-1"), "b", "row-1")
        assertEquals(setOf("row-1"), m["a"]); assertEquals(setOf("row-1"), m["b"])
        assertEquals(mapOf("b" to setOf("row-1")), Bookmarks.toggle(m, "a", "row-1"))
    }

    @Test fun encodeDecodeRoundTripsAndSurvivesGarbage() {
        val m = mapOf("u2-sycon" to setOf("row-5", "practice-q1"), "u4-nereis" to setOf("summary"))
        assertEquals(m, Bookmarks.decode(Bookmarks.encode(m)))
        listOf(null, "", "not json", "[1,2]", "{\"\":[\"x\"]}", "{\"a\":[\"\"]}").forEach {
            assertTrue("'$it'", Bookmarks.decode(it).isEmpty())
        }
    }
}
