package com.gasczoology.invertebratelab.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.NativeRasterCalibration
import com.gasczoology.invertebratelab.data.UnitIIIPixelReviewMark
import com.gasczoology.invertebratelab.data.UnitIIIOriginalPixelEvidence
import com.gasczoology.invertebratelab.data.UnitIIIPlateSpec
import com.gasczoology.invertebratelab.data.UnitIIIReviewFinding

/**
 * A researcher-only crop of THE DECODED ORIGINAL RASTER.
 *
 * No organ positions, subfigure identities, historical licenses or biological
 * approvals are inferred from this local magnifier. The crop is never treated
 * as a source-authenticated pencil master or a student hotspot.
 */
@Composable
internal fun UnitIIISourcePixelEvidenceDialog(
    plate: UnitIIIPlateSpec,
    bitmap: ImageBitmap,
    sourceSha: String,
    mark: UnitIIIPixelReviewMark,
    language: AppLanguage,
    onChange: (UnitIIIPixelReviewMark) -> Unit,
    onClose: () -> Unit,
) {
    val tamil = language == AppLanguage.TAMIL
    val window = remember(bitmap, mark.pixelX, mark.pixelY) {
        UnitIIIOriginalPixelEvidence.window(
            sourceWidth = bitmap.width,
            sourceHeight = bitmap.height,
            x = mark.pixelX,
            y = mark.pixelY,
        )
    }
    val croppedBitmap = remember(bitmap, window) {
        if (window == null) null else runCatching {
            Bitmap.createBitmap(
                bitmap.asAndroidBitmap(),
                window.x, window.y, window.width, window.height,
            ).asImageBitmap()
        }.getOrNull()
    }

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize().testTag("u34-crop-dialog-" + plate.plateId),
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .statusBarsPadding().navigationBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(11.dp),
            ) {
                Text(
                    if (tamil) "அசல் பிக்சல்களின் நெருக்கப் பார்வை — வரைவு மட்டும்"
                    else "Exact original-source pixels — DRAFT inspection",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.testTag("u34-crop-heading-" + plate.plateId),
                )
                Text(
                    if (tamil)
                        "குறியின் பெயர் ஆய்வாளரின் தேர்வு மட்டுமே. பிக்சல் சரி எனப் பார்ப்பது உறுப்பை அறிவியல் முறையில் ஒப்புதல் அளிப்பதல்ல."
                    else
                        "A marker's organ name is a reviewer hypothesis, not verified anatomy. An image crop does not establish scientific approval.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag("u34-anatomy-hold-" + plate.plateId),
                )
                if (window == null || croppedBitmap == null) {
                    Text(
                        if (tamil)
                            "படத்திற்குள்ளான பிக்சல்களை ஆய்வு செய்ய இயலவில்லை. இதை ஏற்றுக்கொள்ளக்கூடாது."
                        else "Original pixel window unavailable; do not accept this marker.",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.testTag("u34-crop-missing-" + plate.plateId),
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(290.dp).clipToBounds()
                            .testTag("u34-source-crop-" + plate.plateId),
                    ) {
                        Image(
                            bitmap = croppedBitmap,
                            contentDescription = if (tamil)
                                "அசல் ஆய்வுப் படத்தின் பிக்சல் பகுதி; தற்காலிகக் குறி"
                            else "Actual original image pixel crop with candidate marker",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit,
                        )
                        val ink = MaterialTheme.colorScheme.error
                        Canvas(Modifier.fillMaxSize()) {
                            val pos = NativeRasterCalibration.project(
                                window.focusX, window.focusY,
                                size.width, size.height,
                                window.width, window.height,
                            )
                            if (pos != null) {
                                drawCircle(
                                    ink, radius = 12.dp.toPx(),
                                    center = Offset(pos.first, pos.second),
                                    style = Stroke(2.dp.toPx()),
                                )
                            }
                        }
                    }
                    Text(
                        "Source SHA-256: " + sourceSha +
                            "\nSource crop x=" + window.x + ", y=" + window.y +
                            ", width=" + window.width + ", height=" + window.height +
                            "\nCandidate " + mark.organId + " · original pixel (" +
                            mark.pixelX + ", " + mark.pixelY + ")",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.testTag("u34-crop-provenance-" + plate.plateId),
                    )
                }
                Text(
                    if (tamil)
                        "காணப்பட்ட நிலை — இவை எதுவும் அங்கீகாரம் அல்ல"
                    else "Human visual finding — NO APPROVED status exists",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.testTag("u34-review-finding-label-" + plate.plateId),
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                        .testTag("u34-review-finding-picker-" + plate.plateId),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(UnitIIIReviewFinding.values().toList(), key = { it.name }) { option ->
                        val title = if (tamil) when(option) {
                            UnitIIIReviewFinding.NOT_REVIEWED -> "ஆய்வு செய்யவில்லை"
                            UnitIIIReviewFinding.VISIBLE_CANDIDATE -> "காணும் வரைவு"
                            UnitIIIReviewFinding.CANNOT_RESOLVE -> "உறுதி இல்லை"
                            UnitIIIReviewFinding.WRONG_STRUCTURE -> "தவறான உறுப்பு"
                        } else when(option) {
                            UnitIIIReviewFinding.NOT_REVIEWED -> "Not reviewed"
                            UnitIIIReviewFinding.VISIBLE_CANDIDATE -> "Visible candidate"
                            UnitIIIReviewFinding.CANNOT_RESOLVE -> "Cannot resolve"
                            UnitIIIReviewFinding.WRONG_STRUCTURE -> "Wrong structure"
                        }
                        FilterChip(
                            selected = mark.finding == option,
                            onClick = { onChange(mark.copy(finding = option)) },
                            label = { Text(title) },
                            modifier = Modifier.heightIn(min = 48.dp)
                                .testTag("u34-finding-" + plate.plateId + "-" + option.name),
                        )
                    }
                }
                OutlinedTextField(
                    value = mark.note,
                    onValueChange = { onChange(mark.copy(
                        note = UnitIIIOriginalPixelEvidence.boundedNote(it),
                    )) },
                    label = {
                        Text(if (tamil) "ஆய்வுக் குறிப்பு; இறுதி ஒப்புதல் அல்ல"
                            else "Reviewer note; NOT a signed approval")
                    },
                    minLines = 2,
                    maxLines = 4,
                    modifier = Modifier.fillMaxWidth()
                        .testTag("u34-review-note-" + plate.plateId),
                )
                Button(
                    onClick = onClose,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                        .testTag("u34-crop-close-" + plate.plateId),
                ) { Text(if(tamil) "மூலப் படத்திற்குத் திரும்பு" else "Back to original source") }
            }
        }
    }
}
