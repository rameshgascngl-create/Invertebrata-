package com.gasczoology.invertebratelab

import android.graphics.BitmapFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.BookFigure
import com.gasczoology.invertebratelab.data.BookFigureLibrary
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
        assertEquals(
            "paramecium/reproduction-approved",
            BookFigureLibrary.stemFor("lib/paramecium-reproduction-approved"),
        )
        val bytes = context.assets.open("paramecium/reproduction-approved.png").use { it.readBytes() }
        val sha = MessageDigest.getInstance("SHA-256")
            .digest(bytes).joinToString("") { "%02x".format(it) }
        assertEquals(
            "8f54a66d89843688d64108cc6ebacae35db497e2bbb8bcbef4c959eefbb44d51",
            sha,
        )
        val decoded = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        assertTrue("Approved plate must decode on Android", decoded != null)
        assertEquals(1536, decoded!!.width)
        assertEquals(1024, decoded.height)
        decoded.recycle()

        val chapter = ParameciumBookChapter.chapter
        val figures = chapter.allBlocks().filterIsInstance<BookFigure>()
        val approved = figures.filter { it.slot == "lib/paramecium-reproduction-approved" }
        assertEquals("Approved plate must appear exactly once", 1, approved.size)
        assertTrue(approved.single().caption.english.isNotBlank())
        assertTrue(approved.single().caption.tamil.any { it.code in 0x0B80..0x0BFF })
    }
}
