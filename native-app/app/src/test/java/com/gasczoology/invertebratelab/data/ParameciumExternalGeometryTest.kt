package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class ParameciumExternalGeometryTest {
    private val plate = ParameciumAnatomyDraft.plates.single {
        it.plateId == "external-cilia"
    }

    @Test
    fun everyProvisionalHotspotMapsToExactlyOneExistingAnatomyFeature() {
        ParameciumExternalGeometry.validateAgainst(plate)
        val specIds = plate.features.map { it.id }
        assertEquals(specIds, ParameciumExternalGeometry.hotspots.map { it.featureId })
        assertEquals(5, ParameciumExternalGeometry.hotspots.size)
        assertEquals(AcademicWorkStatus.DRAFT_UNVERIFIED, plate.status)
        assertTrue(plate.features.all {
            it.geometryKey.startsWith("pending-") && it.touchTargetKey.startsWith("pending-")
        })
    }

    @Test
    fun touchingEachPrototypeCenterSelectsOnlyItsOwnOrgan() {
        for (area in ParameciumExternalGeometry.hotspots) {
            assertEquals(area.featureId,
                ParameciumExternalGeometry.hitNormalized(area.x, area.y))
        }
        assertNull(ParameciumExternalGeometry.hitNormalized(0.03f, 0.03f))
        assertNull(ParameciumExternalGeometry.hitNormalized(-0.1f, 0.5f))
        assertNull(ParameciumExternalGeometry.hitNormalized(1.2f, 0.4f))
        assertNull(ParameciumExternalGeometry.hitNormalized(Float.NaN, 0.4f))
        assertNull(ParameciumExternalGeometry.hitNormalized(0.5f, Float.POSITIVE_INFINITY))
    }

    @Test
    fun viewportUsesOneScaleAndCentersContentWithoutDistortion() {
        val wide = PrototypeCanvasViewport.fit(1080f, 280f)
        assertEquals(280f / 600f, wide.scale, 0.0001f)
        assertEquals(0f, wide.top, 0.0001f)
        assertEquals((1080f - 1000f * wide.scale) / 2f, wide.left, 0.0001f)
        assertEquals(wide.scale * 1000f, wide.contentWidth, 0.0001f)
        assertEquals(wide.scale * 600f, wide.contentHeight, 0.0001f)

        val tall = PrototypeCanvasViewport.fit(400f, 800f)
        assertEquals(0.4f, tall.scale, 0.0001f)
        assertEquals(0f, tall.left, 0.0001f)
        assertEquals(280f, tall.top, 0.0001f)
        assertEquals(400f, tall.contentWidth, 0.0001f)
        assertEquals(240f, tall.contentHeight, 0.0001f)
    }

    @Test
    fun canvasHitsMatchDrawnHotspotsAcrossPortraitAndLandscape() {
        val sizes = listOf(1080f to 280f, 400f to 800f,
            350f to 280f, 1000f to 600f, 1200f to 800f)
        for ((width, height) in sizes) {
            val view = PrototypeCanvasViewport.fit(width, height)
            for (area in ParameciumExternalGeometry.hotspots) {
                val refX = area.x * ParameciumExternalGeometry.REFERENCE_WIDTH
                val refY = area.y * ParameciumExternalGeometry.REFERENCE_HEIGHT
                val screen = view.referenceToCanvas(refX, refY)
                val actual = view.canvasToReference(screen.first, screen.second)
                assertEquals(refX, actual!!.first, 0.0002f)
                assertEquals(refY, actual.second, 0.0002f)
                assertEquals(area.featureId,
                    ParameciumExternalGeometry.hitCanvas(
                        screen.first, screen.second, width, height))
            }
            assertNull(ParameciumExternalGeometry.hitCanvas(0f, 0f, width, height))
        }
    }

    @Test
    fun marginTapsInvalidDimensionsAndDistantPointsDoNotSelectOrgans() {
        val tall = PrototypeCanvasViewport.fit(400f, 800f)
        assertNull(tall.canvasToReference(200f, 200f))
        assertNull(ParameciumExternalGeometry.hitCanvas(200f, 200f, 400f, 800f))
        assertNull(ParameciumExternalGeometry.hitCanvas(200f, 700f, 400f, 800f))
        assertNull(ParameciumExternalGeometry.hitCanvas(20f, 400f, 400f, 800f))
        val wide = PrototypeCanvasViewport.fit(1080f, 280f)
        assertNull(wide.canvasToReference(15f, 100f))
        assertNull(ParameciumExternalGeometry.hitCanvas(15f, 100f, 1080f, 280f))
        assertNull(ParameciumExternalGeometry.hitCanvas(
            Float.NaN, 100f, 1080f, 280f))
        assertNull(ParameciumExternalGeometry.hitCanvas(
            100f, 100f, 0f, 280f))
        assertThrows(IllegalArgumentException::class.java) {
            PrototypeCanvasViewport.fit(Float.POSITIVE_INFINITY, 280f)
        }
        assertThrows(IllegalArgumentException::class.java) {
            PrototypeCanvasViewport.fit(400f, -2f)
        }
    }

    @Test
    fun normalizedAndRealCanvasTapsShareExactlyTheSameReferenceSpaceHitModel() {
        val widthsAndHeights = listOf(
            360f to 280f, 1080f to 280f, 400f to 800f, 1000f to 600f,
        )
        for ((width, height) in widthsAndHeights) {
            val fitted = PrototypeCanvasViewport.fit(width, height)
            for (area in ParameciumExternalGeometry.hotspots) {
                val refRadius = area.radius * ParameciumExternalGeometry.REFERENCE_HEIGHT
                // Just inside, at, and just outside the circular hit boundary.
                for (fraction in listOf(0f, 0.50f, 0.98f, 1.02f, 1.20f)) {
                    val xRef = area.x * ParameciumExternalGeometry.REFERENCE_WIDTH +
                        fraction * refRadius
                    val yRef = area.y * ParameciumExternalGeometry.REFERENCE_HEIGHT
                    val normalizedX = xRef / ParameciumExternalGeometry.REFERENCE_WIDTH
                    val normalizedY = yRef / ParameciumExternalGeometry.REFERENCE_HEIGHT
                    val (pixelX, pixelY) = fitted.referenceToCanvas(xRef, yRef)
                    assertEquals(
                        "Mismatch for " + area.featureId + " at fraction " + fraction,
                        ParameciumExternalGeometry.hitCanvas(
                            pixelX, pixelY, width, height),
                        ParameciumExternalGeometry.hitNormalized(
                            normalizedX, normalizedY),
                    )
                }
            }
        }
    }

    @Test
    fun normalizedCoordinatesDoNotOverSelectAlongLongerReferenceXAxis() {
        // Previously normalized space declared this a pellicle hit (0.07 < 0.087)
        // while native pixel input rejected it (70 reference pixels > 52.2).
        val formerFalsePositiveX = 0.37f
        val y = 0.27f
        assertNull(ParameciumExternalGeometry.hitNormalized(formerFalsePositiveX, y))
        val fit = PrototypeCanvasViewport.fit(360f, 280f)
        val (px, py) = fit.referenceToCanvas(370f, 162f)
        assertNull(ParameciumExternalGeometry.hitCanvas(px, py, 360f, 280f))
    }

    @Test
    fun prototypeCoordinatesRemainWithinCanvasAndUniquelyAssigned() {
        assertTrue(ParameciumExternalGeometry.hotspots.all {
            it.x in 0f..1f && it.y in 0f..1f && it.radius in 0.01f..0.25f
        })
        assertThrows(IllegalArgumentException::class.java) {
            PrototypeHotspot("bad", Float.NaN, 0.1f, 0.1f)
        }
        assertThrows(IllegalArgumentException::class.java) {
            PrototypeHotspot("bad", 0.5f, 0.1f, -0.1f)
        }
        assertThrows(IllegalArgumentException::class.java) {
            ParameciumExternalGeometry.validateAgainst(
                plate.copy(status = AcademicWorkStatus.ACCEPTED)
            )
        }
    }
}
