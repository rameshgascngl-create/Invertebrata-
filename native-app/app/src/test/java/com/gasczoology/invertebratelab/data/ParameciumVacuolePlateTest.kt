package com.gasczoology.invertebratelab.data

import org.junit.Assert.*
import org.junit.Test

class ParameciumVacuolePlateTest {
    @Test fun distinguishOsmoregulatoryComplexesFromDigestiveVacuole() {
        val points = ParameciumVacuolePlate.landmarks
        assertEquals(3, points.size)
        assertEquals(2, points.count { it.organId == "contractile-vacuole" })
        assertEquals(1, points.count { it.organId == "food-vacuole" })
        assertTrue(points.all { it.label.english.isNotBlank() && it.label.tamil.isNotBlank() })
        assertNotEquals(points[0].id, points[1].id)
    }

    @Test fun drawnCentersAndTouchTargetsMatchAtDifferentScreenSizes() {
        for ((width, height) in listOf(
            320f to 280f, 1080f to 640f, 390f to 820f, 1400f to 400f
        )) {
            for (landmark in ParameciumVacuolePlate.landmarks) {
                assertEquals(landmark.id, ParameciumVacuolePlate.hitCanvas(
                    width * landmark.x, height * landmark.y, width, height
                )?.id)
                val edge = landmark.radiusFraction * minOf(width, height)
                val within = ParameciumVacuolePlate.hitCanvas(
                    width * landmark.x + edge * .95f,
                    height * landmark.y, width, height
                )
                assertEquals(landmark.id, within?.id)
            }
            assertNull(ParameciumVacuolePlate.hitCanvas(0f, 0f, width, height))
            assertNull(ParameciumVacuolePlate.hitCanvas(-1f, 10f, width, height))
            assertNull(ParameciumVacuolePlate.hitCanvas(width + 1f, 10f, width, height))
        }
    }

    @Test fun rejectInvalidCanvasInputWithoutSelectingAnOrgan() {
        assertNull(ParameciumVacuolePlate.hitCanvas(
            Float.NaN, 50f, 350f, 300f))
        assertNull(ParameciumVacuolePlate.hitCanvas(
            5f, 10f, 0f, 300f))
        assertNull(ParameciumVacuolePlate.hitCanvas(
            5f, 10f, 350f, Float.POSITIVE_INFINITY))
    }
}
