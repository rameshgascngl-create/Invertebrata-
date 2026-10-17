package com.gasczoology.invertebratelab.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage
import com.gasczoology.invertebratelab.data.UnitIVNereisLessons

/**
 * U5.3: IMAGE-ONLY Nereis atlas. Eight fixed bilingual raster masters are
 * loaded from the APK itself; no network, Canvas anatomy, SVG alternative,
 * tap targets, or selectable anatomical regions.
 *
 * Generated pencil-style reference art must be accurately attributed:
 * NOT presented as an authentic graphite scan, dissection photograph or
 * a college-approved practical plate.
 *
 * Students can see a draft here only after the eight actual source assets
 * have been bundled. A missing image is a visible content defect, never
 * a reason to render the obsolete skeleton-like Canvas substitute.
 */
internal object NereisRasterManifest {
    val filenames = listOf(
        "nereis/01_external_morphology.png",
        "nereis/02_enlarged_head.png",
        "nereis/03_biramous_parapodium.png",
        "nereis/04_digestive_system.png",
        "nereis/05_circulatory_system.png",
        "nereis/06_nervous_system.png",
        "nereis/07_excretory_system.png",
        "nereis/08_trochophore_larva.png",
    )
    init {
        check(filenames.size == 8)
        check(filenames.distinct().size == 8)
    }
}

@Composable
internal fun UnitIVNereisRasterPlate(index: Int, language: AppLanguage) {
    require(index in 0..7) { "Nereis atlas contains exactly eight plates" }
    val ctx = LocalContext.current
    val source = NereisRasterManifest.filenames[index]
    val bitmap = remember(source) {
        try {
            ctx.assets.open(source).use { BitmapFactory.decodeStream(it) }
        } catch (_: Exception) {
            null
        }
    }
    val title = UnitIVNereisLessons.plateTopics[index]
    val ta = language == AppLanguage.TAMIL

    Card(Modifier.fillMaxWidth().testTag("u44-nereis-static-plate-" + (index + 1))) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(title.english, style = MaterialTheme.typography.titleMedium)
            Text(title.tamil, style = MaterialTheme.typography.titleMedium)
            if (bitmap == null || bitmap.width < 1000 || bitmap.height < 1200) {
                Text(
                    if (ta) "தேவையான இருமொழி நெரீஸ் படக் கோப்பு APK-இல் இல்லை. சரிபார்க்கப்படாத மாற்றுப் படம் காட்டப்படவில்லை."
                    else "Required bilingual Nereis raster plate is absent or too small in this APK. No substitute diagram is shown.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.testTag("u53-nereis-raster-missing-" + (index + 1)),
                )
            } else {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = title.english + " — " + title.tamil +
                        ". Eight fixed bilingual source-image plates; no interactive organ hotspots.",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(bitmap.width.toFloat() / bitmap.height.toFloat())
                        .background(Color.White)
                        .border(1.dp, Color.LightGray)
                        .testTag("u44-nereis-pencil-style-drawing-" + (index + 1)),
                )
            }
            // Real Compose text is deliberately retained outside the image
            // for TalkBack accessibility, Tamil editorial comparison, search
            // and font-scale scaling, although printed labels are already
            // embedded in the original image pixels.
            nereisPlateTerms(index).forEachIndexed { termIndex, term ->
                Text(
                    (termIndex + 1).toString() + ". " + term.english + "\n" + term.tamil,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag(
                        "u44-nereis-plate-" + (index + 1) + "-term-" + termIndex
                    ),
                )
            }
            Text(
                if (ta) "பென்சில் பாணியில் உருவாக்கப்பட்ட கற்பித்தல் பட வரைவு. அசல் கையால் வரைந்த ஸ்கேன் அல்ல. தமிழ் பெயர்களுக்கும் உடற்கூறு குறியீடுகளுக்கும் இறுதி கல்வி ஒப்புதல் வழங்கப்படவில்லை."
                else "Generated pencil-style teaching illustration, not an original pencil scan. Tamil terms and leader-line anatomy still require college-manual and independent zoology approval.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("u44-nereis-image-provenance-" + (index + 1)),
            )
        }
    }
}
