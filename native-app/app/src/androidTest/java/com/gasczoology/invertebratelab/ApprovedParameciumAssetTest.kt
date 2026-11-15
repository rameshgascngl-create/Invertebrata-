package com.gasczoology.invertebratelab

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.BookFigure
import com.gasczoology.invertebratelab.data.ParameciumBookChapter
import java.security.MessageDigest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Runs on a device: verifies the approved plate is accessible through the
 * actual packaged Android AssetManager and referenced by the book chapter.
 */
@RunWith(AndroidJUnit4::class)
class ApprovedParameciumAssetTest {
    @Test
    fun approvedReproductionPlateIsPackagedAndReferenced() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val bytes = context.assets.open("paramecium/reproduction-approved.png").use { it.readBytes() }
        val sha = MessageDigest.getInstance("SHA-256")
            .digest(bytes).joinToString("") { "%02x".format(it) }
        assertEquals(
            "8f54a66d89843688d64108cc6ebacae35db497e2bbb8bcbef4c959eefbb44d51",
            sha,
        )
        assertEquals(1536, java.nio.ByteBuffer.wrap(bytes, 16, 4).int)
        assertEquals(1024, java.nio.ByteBuffer.wrap(bytes, 20, 4).int)

        val chapter = ParameciumBookChapter.chapter
        val figures = chapter.allBlocks().filterIsInstance<BookFigure>()
        val approved = figures.filter { it.slot == "paramecium/reproduction-approved" }
        assertEquals("Approved plate must appear exactly once", 1, approved.size)
        assertTrue(approved.single().caption.english.isNotBlank())
        assertTrue(approved.single().caption.tamil.any { it in '\\u0B80'..'\\u0BFF' })
    }
}
