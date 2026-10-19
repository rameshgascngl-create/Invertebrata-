package com.gasczoology.invertebratelab

import android.graphics.BitmapFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/** Verifies debug-only review artwork is bundled and decodable; not scientific approval. */
@RunWith(AndroidJUnit4::class)
class NativeU59ReviewImageMappingTest {
    @Test fun allSeventeenReviewPngsAreDecodable() {
        val assets = InstrumentationRegistry.getInstrumentation().targetContext.assets
        val files = listOf(
            "earthworm/01_earthworm_external_REVIEW_ONLY.png",
            "earthworm/02_earthworm_digestive_REVIEW_ONLY.png",
            "earthworm/03_earthworm_vascular_REVIEW_ONLY.png",
            "earthworm/04_earthworm_nervous_REVIEW_ONLY.png",
            "earthworm/05_earthworm_excretory_REVIEW_ONLY.png",
            "earthworm/06_earthworm_reproductive_REVIEW_ONLY.png",
            "penaeus/07_penaeus_external_REVIEW_ONLY.png",
            "penaeus/08_penaeus_mouthparts_REVIEW_ONLY.png",
            "penaeus/09_penaeus_gills_REVIEW_ONLY.png",
            "penaeus/10_penaeus_internal_REVIEW_ONLY.png",
            "penaeus/11_penaeus_lifecycle_REVIEW_ONLY.png",
            "pila/12_pila_external_REVIEW_ONLY.png",
            "pila/13_pila_internal_REVIEW_ONLY.png",
            "pila/14_pila_radula_REVIEW_ONLY.png",
            "asterias/15_asterias_external_REVIEW_ONLY.png",
            "asterias/16_asterias_water_vascular_REVIEW_ONLY.png",
            "asterias/17_asterias_internal_REVIEW_ONLY.png"
        )
        assertEquals(17, files.size)
        files.forEach { filename ->
            val path = "review_only/u59/$filename"
            val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            assets.open(path).use { BitmapFactory.decodeStream(it, null, bounds) }
            assertTrue("Invalid PNG width: $path", bounds.outWidth > 0)
            assertTrue("Invalid PNG height: $path", bounds.outHeight > 0)
        }
    }
}
