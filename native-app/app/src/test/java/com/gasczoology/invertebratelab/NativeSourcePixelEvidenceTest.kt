package com.gasczoology.invertebratelab

import com.gasczoology.invertebratelab.data.NativeDraftFinding
import com.gasczoology.invertebratelab.data.NativeSourcePixelEvidence
import org.junit.Assert.*
import org.junit.Test

class NativeSourcePixelEvidenceTest {
    @Test fun centeredCropFocusEqualsOriginalFractionalPixel() {
        val win = NativeSourcePixelEvidence.window(500.75f, 250.25f, 1000, 500)!!
        assertEquals(428, win.left)
        assertEquals(178, win.top)
        assertEquals(145, win.width)
        assertEquals(145, win.height)
        assertEquals(72.75f, win.focusX, .0001f)
        assertEquals(72.25f, win.focusY, .0001f)
    }

    @Test fun boundaryCropRemainsInsideSameUnmodifiedRaster() {
        val atOrigin = NativeSourcePixelEvidence.window(1f, 2f, 1000, 500)!!
        assertEquals(0, atOrigin.left)
        assertEquals(0, atOrigin.top)
        assertEquals(1f, atOrigin.focusX, .0001f)
        val bottomRight = NativeSourcePixelEvidence.window(999.9f, 499.9f, 1000, 500)!!
        assertEquals(855, bottomRight.left)
        assertEquals(355, bottomRight.top)
        assertEquals(144.9f, bottomRight.focusX, .0002f)
        assertEquals(144.9f, bottomRight.focusY, .0002f)
        val small = NativeSourcePixelEvidence.window(9f, 13f, 50, 40)!!
        assertEquals(50, small.width)
        assertEquals(40, small.height)
    }

    @Test fun failClosedNoPixelBeyondEdgeOrInvalidGeometry() {
        assertNull(NativeSourcePixelEvidence.window(1000f, 1f, 1000, 500))
        assertNull(NativeSourcePixelEvidence.window(1f, 500f, 1000, 500))
        assertNull(NativeSourcePixelEvidence.window(-.1f, 10f, 1000, 500))
        assertNull(NativeSourcePixelEvidence.window(Float.NaN, 10f, 1000, 500))
        assertNull(NativeSourcePixelEvidence.window(10f, Float.POSITIVE_INFINITY, 1000, 500))
        assertNull(NativeSourcePixelEvidence.window(10f, 10f, 0, 500))
        assertNull(NativeSourcePixelEvidence.window(10f, 10f, 1000, 500, 300))
    }

    @Test fun noAcademicApprovalStateCanBeSelected() {
        val values = NativeDraftFinding.values().map { it.name }.toSet()
        assertEquals(
            setOf("NOT_REVIEWED", "VISIBLE_CANDIDATE", "CANNOT_RESOLVE", "WRONG_STRUCTURE"),
            values,
        )
        assertFalse(values.any { it.contains("APPROV") || it.contains("ACCEPT") })
    }

    @Test fun requireExactImageSha256AndSanitizeNotes() {
        assertTrue(NativeSourcePixelEvidence.isHashBoundSha256("a".repeat(64)))
        assertFalse(NativeSourcePixelEvidence.isHashBoundSha256("a".repeat(63)))
        assertFalse(NativeSourcePixelEvidence.isHashBoundSha256("G".repeat(64)))
        assertFalse(NativeSourcePixelEvidence.isHashBoundSha256(""))
        assertEquals("a".repeat(400), NativeSourcePixelEvidence.cleanNote("a".repeat(800)))
        assertEquals("xy", NativeSourcePixelEvidence.cleanNote("x\u0000y"))
    }
}
