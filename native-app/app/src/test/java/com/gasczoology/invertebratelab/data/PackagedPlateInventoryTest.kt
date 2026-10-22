package com.gasczoology.invertebratelab.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.security.MessageDigest

/**
 * Data-driven replacement for the former hand-typed list of "available" plates.
 * Adding a plate now needs only: the file at
 *   src/main/assets/type_studies/<organism>/<plate-id>.(png|webp)
 * where <plate-id> is listed in NativeCurriculumManifest. Every rule below fails
 * with a message that says exactly which file to rename, convert or remove.
 *
 * Passing here proves the file is well-formed and correctly named. It is NOT
 * scientific, Tamil-editorial or rights approval.
 */
class PackagedPlateInventoryTest {
    private val root = File("src/main/assets/type_studies")

    /** Comparative references shown outside the four mandatory Pila slots. */
    private val comparativeExtras = setOf(
        "pila/shell-specimen-reference", "pila/swainson-1821-pila-ampullacea",
    )

    private fun imageFiles(): List<File> =
        if (!root.isDirectory) emptyList()
        else root.walkTopDown().filter { it.isFile && it.extension.lowercase() in setOf("png", "webp", "jpg", "jpeg", "gif") }.toList()

    private fun stemOf(f: File) = f.relativeTo(root).invariantSeparatorsPath.substringBeforeLast('.')

    @Test fun everyImageIsAKnownSlotWithLowercasePngOrWebpExtension() {
        val valid = NativeCurriculumManifest.typeStudies.flatMap { s ->
            s.requiredPlateIds.map { s.organismId + "/" + it }
        }.toSet() + comparativeExtras
        for (f in imageFiles()) {
            assertTrue("Use a lowercase .png or .webp extension (jpg/gif/UPPERCASE are never loaded): ${f.path}",
                f.extension in setOf("png", "webp"))
            assertTrue("Unknown plate file '${stemOf(f)}'. Valid ids: ${valid.sorted()}",
                stemOf(f) in valid)
        }
    }

    @Test fun headersAreValidAndDimensionsAreSafeForEveryPackagedPlate() {
        for (f in imageFiles()) {
            val bytes = f.readBytes()
            assertTrue("Empty or truncated image: ${f.path}", bytes.size > 1024)
            val (w, h) = RasterHeader.dimensions(bytes)
                ?: error("Not a valid PNG or WebP (header unreadable): ${f.path}")
            assertTrue("Image ${f.path} is ${w}x$h; longest edge must be <= " +
                "${PlateDecodePolicy.MAX_PACKAGED_EDGE_PX}px (run tools/plate_intake.py to resize)",
                maxOf(w, h) <= PlateDecodePolicy.MAX_PACKAGED_EDGE_PX)
            assertTrue("Image ${f.path} is ${w}x$h; shortest edge must be >= 256px", minOf(w, h) >= 256)
        }
    }

    @Test fun eachSlotHasAtMostOneFormat() {
        val byStem = imageFiles().groupBy { stemOf(it) }
        for ((stem, files) in byStem) assertEquals(
            "Both .png and .webp exist for $stem; delete one: ${files.map { it.name }}", 1, files.size)
    }

    @Test fun noTwoSlotsShareIdenticalBytes() {
        val seen = mutableMapOf<String, String>()
        for (f in imageFiles()) {
            val digest = MessageDigest.getInstance("SHA-256").digest(f.readBytes())
                .joinToString("") { "%02x".format(it) }
            val prior = seen.put(digest, stemOf(f))
            assertEquals("Identical image bytes used for '${stemOf(f)}' and '$prior'. " +
                "Each slot needs its own distinct figure.", null, prior)
        }
    }

    /** Informational: prints the slots still waiting for artwork. Never fails. */
    @Test fun reportMissingSlots() {
        val present = imageFiles().map { stemOf(it) }.toSet()
        val missing = NativeCurriculumManifest.typeStudies.flatMap { s ->
            s.requiredPlateIds.map { s.organismId + "/" + it }
        }.filter { it !in present }
        println("PLATES PRESENT: ${35 - missing.size}/35; MISSING (${missing.size}): $missing")
    }
}

/** Reads pixel dimensions from PNG (IHDR) and WebP (VP8 / VP8L / VP8X) headers; null if invalid. */
internal object RasterHeader {
    fun dimensions(b: ByteArray): Pair<Int, Int>? {
        fun be32(o: Int) = ((b[o].toInt() and 255) shl 24) or ((b[o + 1].toInt() and 255) shl 16) or
            ((b[o + 2].toInt() and 255) shl 8) or (b[o + 3].toInt() and 255)
        fun le16(o: Int) = (b[o].toInt() and 255) or ((b[o + 1].toInt() and 255) shl 8)
        fun le24(o: Int) = le16(o) or ((b[o + 2].toInt() and 255) shl 16)
        if (b.size > 24 && b.copyOfRange(0, 8).contentEquals(
                byteArrayOf(-119, 80, 78, 71, 13, 10, 26, 10))) return be32(16) to be32(20)
        if (b.size > 30 && String(b, 0, 4, Charsets.US_ASCII) == "RIFF" &&
            String(b, 8, 4, Charsets.US_ASCII) == "WEBP") {
            return when (String(b, 12, 4, Charsets.US_ASCII)) {
                "VP8X" -> (le24(24) + 1) to (le24(27) + 1)
                "VP8 " -> (le16(26) and 0x3fff) to (le16(28) and 0x3fff)
                "VP8L" -> {
                    val bits = (b[21].toInt() and 255) or ((b[22].toInt() and 255) shl 8) or
                        ((b[23].toInt() and 255) shl 16) or ((b[24].toInt() and 255) shl 24)
                    ((bits and 0x3fff) + 1) to (((bits shr 14) and 0x3fff) + 1)
                }
                else -> null
            }
        }
        return null
    }
}
