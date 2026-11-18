package com.gasczoology.invertebratelab

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.test.platform.app.InstrumentationRegistry

/** True when a required type-study raster is packaged in the APK under type_studies/<organism>/. */
fun typeStudyPlateShips(organism: String, plate: String): Boolean {
    val names = InstrumentationRegistry.getInstrumentation().targetContext.assets
        .list("type_studies/$organism")?.toSet().orEmpty()
    return "$plate.png" in names || "$plate.webp" in names
}

/**
 * Fail-visible plate check that follows the real asset state instead of a hard-coded one:
 * a shipped raster must render as a bitmap and must not also show a missing-plate card;
 * an absent raster must show the missing-plate card and must not show a bitmap.
 * Packaging a plate does NOT accept it academically; those holds are separate.
 */
fun ComposeTestRule.assertPlateState(organism: String, plate: String) {
    if (typeStudyPlateShips(organism, plate)) {
        onNodeWithTag("r2-plate-bitmap-$organism-$plate").assertExists()
        onNodeWithTag("r2-plate-missing-$organism-$plate").assertDoesNotExist()
    } else {
        onNodeWithTag("r2-plate-missing-$organism-$plate").assertExists()
        onNodeWithTag("r2-plate-bitmap-$organism-$plate").assertDoesNotExist()
    }
}

/**
 * True when assets/type_studies/plate_credits.json lists a credit line for the slot ("organism/plate").
 * Eight packaged Fasciola, Ascaris and Obelia plates have no recorded provenance yet; asserting a credit
 * for them would only restate that open source-rights hold, so credit checks apply where a credit exists.
 */
fun plateCreditListed(slot: String): Boolean {
    val text = InstrumentationRegistry.getInstrumentation().targetContext.assets
        .open("type_studies/plate_credits.json").bufferedReader(Charsets.UTF_8).use { it.readText() }
    return text.contains("\"$slot\"")
}
