package com.gasczoology.invertebratelab

import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor
import androidx.compose.ui.geometry.Rect
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertTrue

private val ALLOWED_GALLERY_EVIDENCE = setOf(
    "paramecium-anatomy-gallery-normal.png",
    "paramecium-plate-viewer-normal.png",
    "paramecium-gallery-tamil200.png",
)

/**
 * Real hardware-display screenshot of the plate gallery, saved where run-native-api34-tests.sh
 * collects evidence. Replaces the Canvas-era saveN23bReviewScreenshot for these files: that
 * helper only accepts six old names and demands pencil/cell/highlight pixels that no longer exist.
 *
 * If [plateBounds] and [plateAsset] are given, the screenshot region where that plate is drawn
 * (ContentScale.Fit, box = image aspect) must correlate with the plate's own PNG, so a launcher,
 * blank screen or wrong screen cannot pass. Without them (full-screen viewer) it only checks that
 * a valid, non-trivial PNG was captured; the caller has already asserted the dialog semantics.
 */
internal fun saveGalleryEvidenceScreenshot(
    fileName: String,
    plateBounds: Rect? = null,
    plateAsset: String? = null,
) {
    check(fileName in ALLOWED_GALLERY_EVIDENCE) { "Unexpected evidence file: $fileName" }
    val automation = InstrumentationRegistry.getInstrumentation().uiAutomation
    val file = "/sdcard/Download/native-anatomy-evidence/$fileName"
    ParcelFileDescriptor.AutoCloseInputStream(
        automation.executeShellCommand("screencap -p $file")
    ).use { it.readBytes() }
    val bytes = ParcelFileDescriptor.AutoCloseInputStream(
        automation.executeShellCommand("cat $file")
    ).use { it.readBytes() }
    check(bytes.size > 1000) { "Actual Android screenshot is missing/truncated: $file" }
    val bitmap = checkNotNull(BitmapFactory.decodeByteArray(bytes, 0, bytes.size)) {
        "Android screenshot is not a valid PNG: $file"
    }
    try {
        assertTrue("Screenshot too small: ${bitmap.width}x${bitmap.height}",
            bitmap.width > 100 && bitmap.height > 100)
        if (plateBounds != null && plateAsset != null) {
            assertPlateShowsAsset(bitmap, plateBounds, loadAssetBitmap(plateAsset))
        }
    } finally {
        bitmap.recycle()
    }
}
