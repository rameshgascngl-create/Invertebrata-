package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/** Protects medical-entomology teaching terms against literal machine translation. */
class MedicalVectorTamilTerminologyTest {
    private val data = File("src/main/java/com/gasczoology/invertebratelab/data")

    @Test fun mechanicalVectorMeansCarriageWithoutPathogenDevelopment() {
        val chapter = File(data, "MedicalInsectsBookChapter.kt").readText(Charsets.UTF_8)
        assertTrue(chapter.contains(
            "\"Mechanical transmission\" to \"நோய்க்கிருமியைச் சுமந்து பரப்புதல்\""))
        assertTrue(chapter.contains("நோய்க்கிருமிகளைத் தனது உடலில் சுமந்து பரப்பும்"))
        assertTrue(chapter.contains("No development period required"))
        assertFalse(chapter.contains("இயந்திரவழி"))
    }

    @Test fun biologicalTransmissionDoesNotRequireAllPathogensToHaveAnObligateVector() {
        val chapter = File(data, "MedicalInsectsBookChapter.kt").readText(Charsets.UTF_8)
        assertTrue(chapter.contains(
            "It depends on the pathogen; development or multiplication occurs in the vector"))
        assertFalse(chapter.contains("the pathogen cannot complete its life cycle without it"))
        assertFalse(chapter.contains("which is necessary for its life cycle"))
        assertTrue(chapter.contains("நோய்க்கடத்தியினுள் வளர்ச்சி அல்லது பெருக்கத்துடன் பரவுதல்"))
    }

    @Test fun hostParasiteChapterUsesSameMechanism() {
        val chapter = File(data, "HostParasiteBookChapter.kt").readText(Charsets.UTF_8)
        assertTrue(chapter.contains("கிருமிகளை வளர்ச்சியின்றிச் சுமந்து பரப்பும் நோய்க்கடத்தி"))
        assertFalse(chapter.contains("இயந்திரவழி நோய்க்கடத்தி"))
    }
}
