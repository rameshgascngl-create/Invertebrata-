package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/** Guard the three undergraduate Pila illustrations against missing production assets. */
class PilaProductionFigureTest {
    private val root = File("src/main/assets")
    private val ids = listOf(
        "pila-excretory-system",
        "pila-male-reproductive-system",
        "pila-female-reproductive-system"
    )

    @Test fun pilaFigureSlotsHaveProductionPngsAndExplicitPendingCredits() {
        for (id in ids) {
            val slot = "lib/$id"
            val stem = BookFigureLibrary.stemFor(slot)
            assertNotNull("Missing catalogue slot $slot", stem)
            val image = File(root, "$stem.png")
            assertTrue("Missing production PNG: $image", image.isFile)
            assertTrue("Unexpectedly small PNG: $image", image.length() > 100_000L)
            val bytes = image.inputStream().use { it.readNBytes(24) }
            assertEquals("Not PNG: $image", "89504e470d0a1a0a", bytes.take(8).joinToString("") { "%02x".format(it) })
            val width = java.nio.ByteBuffer.wrap(bytes, 16, 4).int
            val height = java.nio.ByteBuffer.wrap(bytes, 20, 4).int
            assertTrue("Image too small: $image", width >= 1000 && height >= 1000)
            val figure = BookFigureLibrary.all.single { it.id == id }
            assertEquals(FigureAttributionState.PENDING, figure.attributionState)
            org.junit.Assert.assertNull(slot, BookFigureLibrary.creditFor(slot))
            assertTrue("Pending attribution must not imply academic acceptance",
                NativeCurriculumManifest.typeStudies.single { it.organismId == "pila" }.status !=
                    AcademicWorkStatus.ACCEPTED)
        }
    }
}
