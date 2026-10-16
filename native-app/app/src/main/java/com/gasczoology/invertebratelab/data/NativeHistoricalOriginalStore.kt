package com.gasczoology.invertebratelab.data

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.security.MessageDigest

/**
 * U1.17: isolated, byte-verified store for PUBLISHED HISTORICAL FILES.
 *
 * Never write to NativePencilMasterStore: historical printed illustrations
 * and independently supplied hand-pencil scans have different provenance.
 * A valid fingerprint establishes identity ONLY, not biological sign-off.
 */
internal data class NativeHistoricalOriginal(
    val archiveId: String,
    val width: Int,
    val height: Int,
    val sourceSha256: String,
    val originalBytes: ByteArray,
)

internal object NativeHistoricalOriginalStore {
    private const val ORGANISM = "leishmania"

    private fun identity(organism: String): HistoricalArchiveIdentity {
        require(organism == ORGANISM) { "Only catalogued historical sources may be imported" }
        return requireNotNull(UnitIHistoricalPlateArchive.forOrganism(organism))
    }

    private fun sourceFile(context: Context, organism: String): File {
        val record = identity(organism)
        return File(File(context.filesDir, "u117_verified_historical_sources"),
            record.archiveId + ".original")
    }

    /** Accept only the byte-identical published JPEG, not a derivative scan. */
    internal fun validate(organism: String, bytes: ByteArray): NativeHistoricalOriginal {
        val record = identity(organism)
        require(bytes.size == record.sourceByteCount) {
            "Incorrect original-file byte count; no archival identity assigned"
        }
        require(bytes.size >= 3 &&
            bytes[0] == 0xff.toByte() && bytes[1] == 0xd8.toByte() &&
            bytes[2] == 0xff.toByte()) {
            "Original historical source must be the published JPEG file"
        }
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)
        require(record.exactSourceMatches(bytes, bounds.outWidth, bounds.outHeight)) {
            "Published SHA-1, size or image dimensions do not match"
        }
        return NativeHistoricalOriginal(
            archiveId = record.archiveId,
            width = bounds.outWidth,
            height = bounds.outHeight,
            sourceSha256 = MessageDigest.getInstance("SHA-256")
                .digest(bytes).joinToString("") { "%02x".format(it) },
            originalBytes = bytes,
        )
    }

    /** Reject a mismatch BEFORE touching the previously verified original. */
    fun importBytes(context: Context, organism: String, bytes: ByteArray): NativeHistoricalOriginal {
        val checked = validate(organism, bytes)
        val file = sourceFile(context, organism)
        check(file.parentFile!!.isDirectory || file.parentFile!!.mkdirs()) {
            "Cannot create isolated archival source directory"
        }
        val partial = File(file.parentFile, file.name + ".partial")
        try {
            FileOutputStream(partial).use {
                it.write(bytes)
                it.flush()
                it.fd.sync()
            }
            check(partial.renameTo(file)) { "Cannot atomically commit archival original" }
        } finally {
            if (partial.exists()) partial.delete()
        }
        return checked
    }

    /** Android SAF document, bounded to the catalogued original byte count. */
    fun importDocument(context: Context, organism: String, uri: Uri): NativeHistoricalOriginal {
        val record = identity(organism)
        val bytes = context.contentResolver.openInputStream(uri)?.use { input ->
            val out = ByteArrayOutputStream(record.sourceByteCount)
            val chunk = ByteArray(8192)
            while (true) {
                val n = input.read(chunk)
                if (n < 0) break
                require(out.size() + n <= record.sourceByteCount) {
                    "File exceeds published original-file size"
                }
                out.write(chunk, 0, n)
            }
            out.toByteArray()
        } ?: error("Could not open selected Android Files document")
        return importBytes(context, organism, bytes)
    }

    fun load(context: Context, organism: String): NativeHistoricalOriginal? {
        val file = sourceFile(context, organism)
        if (!file.isFile || file.length() != identity(organism).sourceByteCount.toLong()) return null
        return runCatching { validate(organism, file.readBytes()) }.getOrNull()
    }

    fun discard(context: Context, organism: String): Boolean {
        val file = sourceFile(context, organism)
        return !file.exists() || file.delete()
    }
}
