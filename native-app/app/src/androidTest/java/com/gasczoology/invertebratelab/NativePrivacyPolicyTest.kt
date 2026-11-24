package com.gasczoology.invertebratelab

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.platform.app.InstrumentationRegistry
import com.gasczoology.invertebratelab.data.NativeLearningRepository
import com.gasczoology.invertebratelab.data.NativeLearningState
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

/** The privacy notice must be available offline and accessible from the home screen. */
class NativePrivacyPolicyTest {
    @get:Rule val composeRule = createAndroidComposeRule<MainActivity>()

    @Test fun privacyPolicyOpensAndClosesFromHome() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        runBlocking { NativeLearningRepository(context).save(NativeLearningState()) }
        composeRule.activityRule.scenario.recreate()
        composeRule.waitUntil(20_000) {
            composeRule.onAllNodesWithTag("native-home").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag("native-home").performScrollToNode(hasTestTag("privacy-policy-open"))
        composeRule.onNodeWithTag("privacy-policy-open").performClick()
        composeRule.onNodeWithTag("privacy-dialog").assertIsDisplayed()
        composeRule.onNodeWithText("rameshgascngl@gmail.com", substring = true).assertExists()
        composeRule.onNodeWithTag("privacy-close").performClick()
        composeRule.onNodeWithTag("privacy-dialog").assertDoesNotExist()
        composeRule.onNodeWithTag("privacy-policy-open").assertIsDisplayed()
    }
}
