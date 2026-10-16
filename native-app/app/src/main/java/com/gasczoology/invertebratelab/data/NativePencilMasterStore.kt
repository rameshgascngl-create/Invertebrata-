package com.gasczoology.invertebratelab.data

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.security.MessageDigest

/**
 * Offline Android internal storage for REVIEW-CANDIDATE pencil scans.
 *
 * This does not infer medium, morphology, authorship, anatomical accuracy or
 * image provenance. An accepted pixel stream is always UNVERIFIED.
 * All paths are fixed by allowlisted organism IDs, not untrusted URIs/filenames.
 */
internal data class NativePencilCandidate(
    val organismId: String,
    val format: String,
    val width: Int,
    val height: Int,
    val sourceSha256: String,
    val originalBytes: ByteArray,
)

internal object NativePencilMasterStore {
    private const val MAX_BYTES = 12 * 1024 * 1024
    private const val MAX_PIXELS = 10_000_000L
    private val organismIds = setOf("trypanosoma", "leishmania")
    private val pngMagic = byteArrayOf(137.toByte(), 80, 78, 71, 13, 10, 26, 10)

    private fun trustedOrganism(organismId: String): String {
        require(organismId in organismIds) { "Organism not approved for image intake" }
        return organismId
    }

    private fun location(context: Context, organismId: String): File {
        return File(
            File(context.filesDir, "u110_unverified_pencil_candidates"),
            trustedOrganism(organismId) + ".source",
        )
    }

    internal fun sniffFormat(bytes: ByteArray): String? = when {
        bytes.size >= pngMagic.size &&
            pngMagic.indices.all { bytes[it] == pngMagic[it] } -> "png"
        bytes.size >= 4 &&
            bytes[0] == 0xff.toByte() && bytes[1] == 0xd8.toByte() &&
            bytes[2] == 0xff.toByte() -> "jpeg"
        else -> null
    }

    internal fun allowedDimensions(width: Int, height: Int): Boolean =
        width in 600..5000 && height in 600..5000 &&
            width.toLong() * height.toLong() <= MAX_PIXELS

    private fun digest(bytes: ByteArray): String = MessageDigest
        .getInstance("SHA-256").digest(bytes)
        .joinToString("") { "%02x".format(it) }

    private fun inspect(organismId: String, bytes: ByteArray): NativePencilCandidate {
        trustedOrganism(organismId)
        require(bytes.size in 4_096..MAX_BYTES) {
            "Image too small or exceeds the 12 MiB memory-safe intake limit"
        }
        val format = requireNotNull(sniffFormat(bytes)) {
            "Only actual PNG or JPEG source bytes are allowed"
        }
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)
        require(allowedDimensions(bounds.outWidth, bounds.outHeight)) {
            "Source plate must have 600–5000px dimensions, maximum 10MP"
        }
        val raster = BitmapFactory.decodeByteArray(
            bytes, 0, bytes.size,
            BitmapFactory.Options().apply { inPreferredConfig = android.graphics.Bitmap.Config.ARGB_8888 },
        ) ?: error("Image cannot be decoded")
        val actualWidth = raster.width
        val actualHeight = raster.height
        raster.recycle()
        require(actualWidth == bounds.outWidth && actualHeight == bounds.outHeight) {
            "Source image bounds changed during decode"
        }
        return NativePencilCandidate(
            organismId = organismId,
            format = format,
            width = actualWidth,
            height = actualHeight,
            sourceSha256 = digest(bytes),
            originalBytes = bytes,
        )
    }

    /**
     * Copies bytes to app-private storage, never source permissions or cloud.
     * A temp write and rename prevent loading partial candidate files.
     */
    fun importBytes(context: Context, organismId: String, bytes: ByteArray): NativePencilCandidate {
        val checked = inspect(organismId, bytes)
        val file = location(context, organismId)
        check(file.parentFile!!.isDirectory || file.parentFile!!.mkdirs()) {
            "Cannot create candidate storage"
        }
        val partial = File(file.parentFile, file.name + ".partial")
        try {
            FileOutputStream(partial).use { output ->
                output.write(bytes)
                output.flush()
                output.fd.sync()
            }
            check(partial.renameTo(file)) { "Cannot atomically replace review candidate" }
        } finally {
            if (partial.exists()) partial.delete()
        }
        return checked
    }

    fun importDocument(context: Context, organismId: String, uri: Uri): NativePencilCandidate {
        trustedOrganism(organismId)
        val bytes = context.contentResolver.openInputStream(uri)?.use { input ->
            val output = ByteArrayOutputStream()
            val chunk = ByteArray(16 * 1024)
            while (true) {
                val n = input.read(chunk)
                if (n < 0) break
                check(output.size() + n <= MAX_BYTES) {
                    "Selected file exceeds the 12 MiB intake limit"
                }
                output.write(chunk, 0, n)
            }
            output.toByteArray()
        } ?: error("Selected document could not be opened")
        return importBytes(context, organismId, bytes)
    }

    fun load(context: Context, organismId: String): NativePencilCandidate? {
        val file = location(context, organismId)
        if (!file.isFile || file.length() !in 4_096L..MAX_BYTES.toLong()) return null
        return runCatching { inspect(organismId, file.readBytes()) }.getOrNull()
    }

    fun discard(context: Context, organismId: String): Boolean {
        val file = location(context, organismId)
        return !file.exists() || file.delete()
    }
}
