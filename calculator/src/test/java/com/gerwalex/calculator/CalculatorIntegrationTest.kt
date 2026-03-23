package com.gerwalex.calculator

import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gerwalex.calculator.arithmatic.CalculatorBrain
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(
    sdk = [33],
)
class CalculatorIntegrationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testFullCalculation_5_plus_3_equals_8() {
        val brain = CalculatorBrain()

        composeTestRule.setContent {
            // Wir beobachten den State des ViewModels
            val state by brain.state.collectAsState()

            CalculatorLayout(
                state = state,
                onAction = { brain.onAction(it) },
                onNumber = { brain.onNumberAction(it) }
            )
        }

        // 1. Klicke "5"
        // Wir suchen nach dem Text, der im Button steht (symbol.type)
        composeTestRule.onNodeWithText("5").performClick()

        // 2. Klicke "+"
        // Hier nutzen wir die contentDescription, falls das Symbol ein Icon wäre
        // oder einfach den Text "+", falls symbol.type "+" zurückgibt.
        composeTestRule.onNodeWithText("+").performClick()

        // 3. Klicke "3"
        composeTestRule.onNodeWithText("3").performClick()

        // 4. Klicke "="
        composeTestRule.onNodeWithText("=").performClick()

        // 5. Überprüfung des Displays
        // Dein Layout zeigt state.inputString groß an.
        composeTestRule.onNodeWithText("8").assertExists()

    }

    @Test
    fun testBackspace_switchesToPendingMemory() {
        val brain = CalculatorBrain()

        composeTestRule.setContent {
            // Wir beobachten den State des ViewModels
            val state by brain.state.collectAsState()

            CalculatorLayout(
                state = state,
                onAction = { brain.onAction(it) },
                onNumber = { brain.onNumberAction(it) }
            )
        }

        // Setup: Wir haben "12" getippt und "+" gedrückt, dann "5" getippt
        // ... Logik-Aufrufe oder Klicks ...

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("5").performClick()

        // Jetzt Backspace -> aus "5" wird "0"
        composeTestRule.onNodeWithText("⌫").performClick() // Nutze das Symbol deines Enums

        // Nochmal Backspace -> Laut deiner Logik sollte jetzt wieder "12" dastehen
        composeTestRule.onNodeWithText("⌫").performClick()

        composeTestRule.onNodeWithText("12").assertExists()
    }
}