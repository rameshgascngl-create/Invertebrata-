package com.gasczoology.invertebratelab

import com.gasczoology.invertebratelab.data.HistoricalArchiveIdentity
import com.gasczoology.invertebratelab.data.HistoricalPixelCrop
import com.gasczoology.invertebratelab.data.UnitIHistoricalPlateArchive
import org.junit.Assert.*
import org.junit.Test
import java.security.MessageDigest

class UnitIHistoricalPlateArchiveTest {
    @Test fun publishedHistoricalSourceIsScopedToLeishmaniaAndHasProvenance() {
        val record = UnitIHistoricalPlateArchive.leishmania1922
        assertNull(UnitIHistoricalPlateArchive.forOrganism("trypanosoma"))
        assertEquals(record, UnitIHistoricalPlateArchive.forOrganism("leishmania"))
        assertEquals(314025, record.sourceByteCount)
        assertEquals(1552, record.sourceWidth)
        assertEquals(1584, record.sourceHeight)
        assertEquals("3a08d99564510b548416ffb0bb4318eef68c1948",
            record.publishedSha1)
        assertTrue(record.sourcePage.contains("Leishmania_donovani_"))
        assertEquals(listOf("tissue-a-e", "culture-f-o", "flagellated-j-l"),
            record.figureGroups.map { it.id })
        assertTrue(record.figureGroups.all { it.stageClassification.startsWith("UNVERIFIED_") })
        assertTrue(record.figureGroups.first { it.id=="culture-f-o" }
            .captionEn.contains("do not classify every form"))
    }

    @Test fun checksumMustMatchOriginalBytesDimensionsAndLength() {
        val fixture = "a private unit test, not a historical plate".toByteArray()
        val sha = MessageDigest.getInstance("SHA-1").digest(fixture)
            .joinToString("") { "%02x".format(it) }
        val example = HistoricalArchiveIdentity(
            "fixture-only",
            "https://commons.wikimedia.org/wiki/File:Fixture",
            "https://commons.wikimedia.org/fixture",
            "Unit test", "fixture",
            fixture.size, 100, 110, sha, emptyList(),
        )
        assertTrue(example.exactSourceMatches(fixture, 100, 110))
        assertFalse(example.exactSourceMatches(fixture, 101, 110))
        assertFalse(example.exactSourceMatches(fixture, 100, 111))
        assertFalse(example.exactSourceMatches(fixture + 0.toByte(), 100, 110))
        val modified = fixture.clone()
        modified[0] = (modified[0].toInt() xor 1).toByte()
        assertFalse(example.exactSourceMatches(modified, 100, 110))
        assertFalse(UnitIHistoricalPlateArchive.leishmania1922.exactSourceMatches(
            fixture, 1552, 1584
        ))
    }

    @Test fun sourcePixelCropIsExclusiveExactAndFailsClosed() {
        val c = HistoricalPixelCrop.between(
            503.2f, 845.1f, 200.3f, 420.1f, 1552, 1584,
        )!!
        assertEquals(200, c.left)
        assertEquals(420, c.top)
        assertEquals(504, c.rightExclusive)
        assertEquals(846, c.bottomExclusive)
        assertEquals(304, c.width)
        assertEquals(426, c.height)
        assertTrue(c.inBounds(1552, 1584))
        assertNull(HistoricalPixelCrop.between(
            -1f, 200f, 60f, 300f, 1552, 1584,
        ))
        assertNull(HistoricalPixelCrop.between(
            0f, 0f, 1553f, 150f, 1552, 1584,
        ))
        assertNull(HistoricalPixelCrop.between(
            300f, 300f, 320f, 340f, 1552, 1584,
        ))
        assertNull(HistoricalPixelCrop.between(
            Float.NaN, 100f, 200f, 300f, 1552, 1584,
        ))
    }
}
