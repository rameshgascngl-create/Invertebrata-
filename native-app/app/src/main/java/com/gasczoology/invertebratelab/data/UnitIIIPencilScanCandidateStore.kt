package com.gasczoology.invertebratelab.data

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.security.MessageDigest

/**
 * Separate U3.6 reviewer-only intake for all SIX Unit III plate slots.
 *
 * "Candidate" is a file-integrity designation only. This cannot determine
 * whether an image is actually drawn in pencil, scientifically correct,
 * properly licensed or approved for display to students. Original APK
 * artwork and the Unit I pencil-store entries are never overwritten.
 */
internal data class UnitIIIPencilScanCandidate(
    val plateStem: String,
    val width: Int,
    val height: Int,
    val format: String,
    val sourceSha256: String,
    val originalBytes: ByteArray,
) {
    val approvalStatus: String get() = "UNVERIFIED_USER_SUPPLIED_SOURCE"
}

internal object UnitIIIPencilScanCandidateStore {
    private const val MIN_BYTES = 4096
    private const val MAX_BYTES = 12 * 1024 * 1024
    private const val DIRECTORY = "u36_unit3_unverified_pencil_scan_candidates"

    private fun requireStem(stem: String): UnitIIIPlateSpec =
        requireNotNull(UnitIIIPlateCatalog.plates.firstOrNull { it.assetStem == stem }) {
            "Source not in the six Unit III curriculum plate slots"
        }

    private fun target(context: Context, stem: String): File {
        val plate = requireStem(stem)
        return File(File(context.filesDir, DIRECTORY),
            plate.organismId + "_" + plate.plateId + ".original")
    }

    /** Fail closed BEFORE writing/replacing any previously imported candidate. */
    internal fun validate(stem: String, bytes: ByteArray): UnitIIIPencilScanCandidate {
        requireStem(stem)
        require(bytes.size in MIN_BYTES..MAX_BYTES) {
            "Image must be at least 4 KiB and at most 12 MiB"
        }
        val format = requireNotNull(NativePencilMasterStore.sniffFormat(bytes)) {
            "Only original PNG/JPEG file signatures accepted"
        }
        require(NativePencilMasterStore.sourceOrientationAccepted(format, bytes)) {
            "JPEG EXIF rotation/mirroring is unsafe for source-pixel calibration"
        }
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)
        require(NativePencilMasterStore.allowedDimensions(
            bounds.outWidth, bounds.outHeight)) {
            "Source dimensions must be 600–5000px and no more than 10MP"
        }
        // Prove that the file is actually decodable, not just a spoofed header.
        // Use a bounded thumbnail in memory; original bytes remain untouched.
        val factor = sequenceOf(1,2,4,8).firstOrNull {
            maxOf(bounds.outWidth / it, bounds.outHeight / it) <= 1600
        } ?: 8
        val sample = BitmapFactory.Options().apply { inSampleSize = factor }
        val decoded = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, sample)
            ?: error("Invalid image pixel data")
        decoded.recycle()
        val sha = MessageDigest.getInstance("SHA-256").digest(bytes)
            .joinToString("") { "%02x".format(it) }
        return UnitIIIPencilScanCandidate(stem,bounds.outWidth,bounds.outHeight,
            format,sha,bytes)
    }

    fun importBytes(context: Context, stem: String, bytes: ByteArray):
        UnitIIIPencilScanCandidate {
        val candidate = validate(stem, bytes)
        val dest = target(context, stem)
        check(dest.parentFile!!.isDirectory || dest.parentFile!!.mkdirs())
        val temp = File(dest.parentFile, dest.name + ".partial")
        try {
            FileOutputStream(temp).use {
                it.write(bytes)
                it.flush()
                it.fd.sync()
            }
            check(temp.renameTo(dest)) { "Could not commit local source candidate" }
        } finally {
            if (temp.exists()) temp.delete()
        }
        return candidate
    }

    fun importDocument(context: Context, stem: String, uri: Uri):
        UnitIIIPencilScanCandidate {
        requireStem(stem)
        val bytes = context.contentResolver.openInputStream(uri)?.use { input ->
            val output = ByteArrayOutputStream()
            val chunk = ByteArray(8192)
            while (true) {
                val count = input.read(chunk)
                if (count < 0) break
                require(output.size() + count <= MAX_BYTES) {
                    "Original exceeds 12 MiB import bound"
                }
                output.write(chunk, 0, count)
            }
            output.toByteArray()
        } ?: error("Android Files document unavailable")
        return importBytes(context, stem, bytes)
    }

    fun load(context: Context, stem: String): UnitIIIPencilScanCandidate? {
        val file = target(context, stem)
        if (!file.isFile || file.length() !in MIN_BYTES.toLong()..MAX_BYTES.toLong())
            return null
        return runCatching { validate(stem,file.readBytes()) }.getOrNull()
    }

    fun discard(context: Context, stem: String): Boolean {
        val file = target(context, stem)
        return !file.exists() || file.delete()
    }
}
