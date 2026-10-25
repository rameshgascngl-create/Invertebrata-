package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/** Fail closed: reference PNGs may be reviewed, but not bundled or routed as approved stage plates. */
class ParameciumReproductionCandidateIsolationTest {
    @Test fun unapprovedReferencePngsAreNotStudentFacing() {
        val appAssets = File("src/main/assets/unit1/reference")
        for (stem in listOf("paramecium_parker_fission_reference", "paramecium_conjugation_reference")) {
            assertFalse("Unreviewed reference bundled in main assets: $stem", File(appAssets, "$stem.png").exists())
        }
        for (fileName in listOf("INVERTEBRATA_Parker_reference_review.png", "INVERTEBRATA_Conjugation_reference_review.png")) {
            val reference = File("../$fileName")
            assertTrue("Review-only source reference absent: $fileName", reference.isFile)
            assertTrue("Review-only reference is not PNG: $fileName",
                reference.inputStream().use { stream ->
                    stream.readNBytes(8).contentEquals(byteArrayOf(0x89.toByte(), 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a))
                })
        }
    }
}
