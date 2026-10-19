package com.gasczoology.invertebratelab.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gasczoology.invertebratelab.data.AppLanguage

/** Debug-asset-only review: never interprets an unapproved illustration as verified. */
@Composable
internal fun U59DebugAnatomyPlate(organism: String, sectionId: String, language: AppLanguage) {
    val filename = when (organism to sectionId) {
        "penaeus" to "external" -> "07_penaeus_external_REVIEW_ONLY.png"
        "penaeus" to "appendage-architecture" -> "08_penaeus_mouthparts_REVIEW_ONLY.png"
        "penaeus" to "respiration" -> "09_penaeus_gills_REVIEW_ONLY.png"
        "penaeus" to "nutrition" -> "10_penaeus_internal_REVIEW_ONLY.png"
        "penaeus" to "development" -> "11_penaeus_lifecycle_REVIEW_ONLY.png"
        "pila" to "external" -> "12_pila_external_REVIEW_ONLY.png"
        "pila" to "organs" -> "13_pila_internal_REVIEW_ONLY.png"
        "pila" to "process" -> "14_pila_radula_REVIEW_ONLY.png"
        "asterias" to "external" -> "15_asterias_external_REVIEW_ONLY.png"
        "asterias" to "water-vascular" -> "16_asterias_water_vascular_REVIEW_ONLY.png"
        "asterias" to "internal" -> "17_asterias_internal_REVIEW_ONLY.png"
        else -> null
    } ?: return
    val context = LocalContext.current
    val bitmap = remember(context, organism, filename) {
        runCatching {
            context.assets.open("review_only/u59/$organism/$filename").use {
                BitmapFactory.decodeStream(it)?.asImageBitmap()
            }
        }.getOrNull()
    } ?: return
    Column(verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth().testTag("u59-review-$organism-$sectionId")) {
        Text(if (language == AppLanguage.TAMIL)
            "ஆய்வுக்கான படம் மட்டும் — உடற்கூறு மற்றும் தமிழ் பெயர்கள் சரிபார்க்கப்படவில்லை"
            else "DEBUG REVIEW ONLY — anatomical and Tamil-label approval pending",
            color = MaterialTheme.colorScheme.error)
        Image(bitmap = bitmap, contentDescription = "$organism $sectionId — unverified review image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth().testTag("u59-review-image-$organism-$sectionId"))
    }
}
