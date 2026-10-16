package com.gasczoology.invertebratelab

import com.gasczoology.invertebratelab.data.NativeDraftPixelRefinement
import com.gasczoology.invertebratelab.data.RasterPixelLocation
import org.junit.Assert.*
import org.junit.Test

class NativeDraftPixelRefinementTest {
    private val point = RasterPixelLocation(
        pixelX = 501.25f,
        pixelY = 203.5f,
        normalizedX = .50125f,
        normalizedY = .407f,
    )

    @Test fun onePixelStepsRetainFractionalPrecisionAndNormalizeToMaster() {
        val right = NativeDraftPixelRefinement.nudge(point, 1, 0, 1000, 500)!!
        assertEquals(502.25f, right.pixelX, .00001f)
        assertEquals(.50225f, right.normalizedX, .00001f)
        assertEquals(203.5f, right.pixelY, .00001f)
        val back = NativeDraftPixelRefinement.nudge(right, -1, 0, 1000, 500)!!
        assertEquals(point.pixelX, back.pixelX, .00001f)
        assertEquals(point.normalizedY, back.normalizedY, .00001f)
        val down = NativeDraftPixelRefinement.nudge(point, 0, 1, 1000, 500)!!
        assertEquals(204.5f, down.pixelY, .00001f)
        assertEquals(.409f, down.normalizedY, .00001f)
    }

    @Test fun rejectsOffImageRatherThanClampingToBoundary() {
        val edge = RasterPixelLocation(999.5f, 2f, .9995f, .004f)
        assertNull(NativeDraftPixelRefinement.nudge(edge, 1, 0, 1000, 500))
        assertNull(NativeDraftPixelRefinement.nudge(point, 0, 1, 0, 500))
        assertNull(NativeDraftPixelRefinement.nudge(point, 0, 1, 1000, -1))
        assertNull(NativeDraftPixelRefinement.nudge(point, 0, 0, 1000, 500))
        assertNull(NativeDraftPixelRefinement.nudge(point, 2, 0, 1000, 500))
        assertNull(NativeDraftPixelRefinement.nudge(point, 0, -2, 1000, 500))
        assertNull(NativeDraftPixelRefinement.nudge(
            point.copy(pixelX = Float.NaN), 1, 0, 1000, 500
        ))
    }

    @Test fun returnedSourceCoordinatesProjectIntoActualNativeViewport() {
        val refined = NativeDraftPixelRefinement.nudge(point, -1, -1, 1000, 500)!!
        val (x, y) = com.gasczoology.invertebratelab.data.NativeRasterCalibration.project(
            pixelX = refined.pixelX, pixelY = refined.pixelY,
            viewportWidth = 340f, viewportHeight = 360f,
            rasterWidth = 1000, rasterHeight = 500,
            zoom = 2.4f, panX = 30f, panY = -19f,
        )!!
        val hit = com.gasczoology.invertebratelab.data.NativeRasterCalibration.hit(
            tapX = x, tapY = y,
            viewportWidth = 340f, viewportHeight = 360f,
            rasterWidth = 1000, rasterHeight = 500,
            zoom = 2.4f, panX = 30f, panY = -19f,
        )!!
        assertEquals(refined.pixelX, hit.pixelX, .001f)
        assertEquals(refined.pixelY, hit.pixelY, .001f)
    }
}
