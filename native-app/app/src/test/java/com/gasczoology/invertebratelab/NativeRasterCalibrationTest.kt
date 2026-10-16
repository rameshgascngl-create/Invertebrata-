package com.gasczoology.invertebratelab

import com.gasczoology.invertebratelab.data.NativeRasterCalibration
import org.junit.Assert.*
import org.junit.Test

class NativeRasterCalibrationTest {
    private fun close(expected: Float, actual: Float) =
        assertEquals(expected.toDouble(), actual.toDouble(), .015)

    @Test fun sourcePixelMapsThroughLetterboxedFit() {
        // Image 1000x500 fits within 200x400 with 150px top/bottom padding.
        val center = NativeRasterCalibration.hit(
            tapX = 100f, tapY = 200f,
            viewportWidth = 200f, viewportHeight = 400f,
            rasterWidth = 1000, rasterHeight = 500,
        )!!
        close(500f, center.pixelX)
        close(250f, center.pixelY)
        close(.5f, center.normalizedX)
        close(.5f, center.normalizedY)
        assertNull(NativeRasterCalibration.hit(
            tapX = 100f, tapY = 40f,
            viewportWidth = 200f, viewportHeight = 400f,
            rasterWidth = 1000, rasterHeight = 500,
        ))
    }

    @Test fun inverseAndForwardAgreeWhenZoomedAndPanned() {
        // Fit rect: 300x150 centred within 300x300.
        // Source (200,100) -> (100,125) before zoom, -> (85,75) at zoom 2/pan.
        val point = NativeRasterCalibration.project(
            pixelX = 200f, pixelY = 100f,
            viewportWidth = 300f, viewportHeight = 300f,
            rasterWidth = 600, rasterHeight = 300,
            zoom = 2f, panX = 35f, panY = -25f,
        )!!
        close(85f, point.first)
        close(75f, point.second)
        val inverse = NativeRasterCalibration.hit(
            tapX = point.first, tapY = point.second,
            viewportWidth = 300f, viewportHeight = 300f,
            rasterWidth = 600, rasterHeight = 300,
            zoom = 2f, panX = 35f, panY = -25f,
        )!!
        close(200f, inverse.pixelX)
        close(100f, inverse.pixelY)
        close(1f/3f, inverse.normalizedX)
        close(1f/3f, inverse.normalizedY)
    }

    @Test fun imagePixelRemainsIdenticalAcrossPortraitAndLandscape() {
        val sourceX = 780f
        val sourceY = 245f
        val screenPortrait = NativeRasterCalibration.project(
            pixelX = sourceX, pixelY = sourceY,
            viewportWidth = 280f, viewportHeight = 500f,
            rasterWidth = 2100, rasterHeight = 1900,
        )!!
        val screenLandscape = NativeRasterCalibration.project(
            pixelX = sourceX, pixelY = sourceY,
            viewportWidth = 800f, viewportHeight = 320f,
            rasterWidth = 2100, rasterHeight = 1900,
        )!!
        listOf(
            NativeRasterCalibration.hit(
                screenPortrait.first, screenPortrait.second,
                280f, 500f, 2100, 1900
            ),
            NativeRasterCalibration.hit(
                screenLandscape.first, screenLandscape.second,
                800f, 320f, 2100, 1900
            ),
        ).forEach { hit ->
            close(sourceX, hit!!.pixelX)
            close(sourceY, hit.pixelY)
        }
    }

    @Test fun refuseImpossibleViewportsAndNonfiniteZoom() {
        assertNull(NativeRasterCalibration.hit(10f, 10f, 0f, 300f, 100, 200))
        assertNull(NativeRasterCalibration.hit(10f, 10f, 100f, 300f, 0, 200))
        assertNull(NativeRasterCalibration.hit(
            10f, 10f, 100f, 300f, 100, 200, zoom = Float.NaN
        ))
        assertNull(NativeRasterCalibration.hit(
            10f, 10f, 100f, 300f, 100, 200, zoom = 0f
        ))
        assertNull(NativeRasterCalibration.hit(
            100000f, 100000f, 100f, 300f, 100, 200
        ))
        assertNull(NativeRasterCalibration.project(
            -1f, 2f, 100f, 100f, 400, 200
        ))
    }
    @Test fun sourceRasterRightAndBottomEdgesAreNotRealPixels() {
        // 1000x500 source shown in a 200x400 ContentScale.Fit viewport:
        // image occupies x=0..200 and y=150..250 (edge endpoints excluded).
        assertNull(NativeRasterCalibration.hit(
            200f, 200f, 200f, 400f, 1000, 500,
        ))
        assertNull(NativeRasterCalibration.hit(
            100f, 250f, 200f, 400f, 1000, 500,
        ))
        assertNotNull(NativeRasterCalibration.hit(
            199.9f, 249.9f, 200f, 400f, 1000, 500,
        ))
        assertNull(NativeRasterCalibration.project(
            1000f, 250f, 200f, 400f, 1000, 500,
        ))
        assertNull(NativeRasterCalibration.project(
            500f, 500f, 200f, 400f, 1000, 500,
        ))
        assertNull(NativeRasterCalibration.project(
            Float.NaN, 5f, 200f, 400f, 1000, 500,
        ))
    }

}
