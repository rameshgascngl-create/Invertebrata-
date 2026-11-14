package com.gasczoology.invertebratelab

import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

/** The privacy notice must be available offline and accessible from the home screen. */
class NativePrivacyPolicyTest {
    @get:Rule val composeRule = createAndroidComposeRule<MainActivity>()

    @Test fun privacyPolicyOpensAndClosesFromHome() {
        composeRule.onNodeWithTag("native-home").assertExists()
        composeRule.onNodeWithTag("privacy-policy-open").performClick()
        composeRule.onNodeWithTag("privacy-dialog").assertExists()
        composeRule.onNodeWithTag("privacy-close").performClick()
        composeRule.onNodeWithTag("privacy-policy-open").assertExists()
    }
}
