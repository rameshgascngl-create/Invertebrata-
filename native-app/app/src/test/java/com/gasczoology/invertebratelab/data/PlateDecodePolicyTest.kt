package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertThrows
import org.junit.Test

class PlateDecodePolicyTest {
    @Test fun plateWithinLimitIsDecodedAtFullResolution() {
        assertEquals(1, PlateDecodePolicy.sampleSizeFor(1700, 1900))
        assertEquals(1, PlateDecodePolicy.sampleSizeFor(2292, 2483)) // largest production plate
        assertEquals(1, PlateDecodePolicy.sampleSizeFor(PlateDecodePolicy.MAX_EDGE_PX, 10))
    }

    @Test fun oversizedMasterIsHalvedUntilItFits() {
        assertEquals(2, PlateDecodePolicy.sampleSizeFor(4608, 3546))
        assertEquals(2, PlateDecodePolicy.sampleSizeFor(4608, 4140))
        assertEquals(4, PlateDecodePolicy.sampleSizeFor(8000, 6000))
    }

    @Test fun decodedMemoryIsBoundedForAnyInput() {
        val worst = PlateDecodePolicy.decodedBytes(12000, 9000)
        assertTrue("decoded size $worst", worst <= 2560L * 2560L * 4L)
        // The unbounded decode of the review-only starfish master was ~76 MB.
        assertTrue(PlateDecodePolicy.decodedBytes(4608, 4140) < 30L * 1024 * 1024)
    }

    @Test fun degenerateInputsAreSafe() {
        assertEquals(1, PlateDecodePolicy.sampleSizeFor(0, 0))
        assertEquals(1, PlateDecodePolicy.sampleSizeFor(-5, 100))
        assertThrows(IllegalArgumentException::class.java) {
            PlateDecodePolicy.sampleSizeFor(100, 100, 0)
        }
    }
}
