package com.gerwalex.calculator

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gerwalex.calculator.arithmatic.CalculatorBrain
import org.junit.Test

class CalculatorIntegrationTest : CalculatorTest() {


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
        composeTestRule.onNodeWithTag("+").performClick()

        // 3. Klicke "3"
        composeTestRule.onNodeWithTag("3").performClick()

        // 4. Klicke "="
        composeTestRule.onNodeWithTag("=").performClick()

        // 5. Überprüfung des Displays
        // Dein Layout zeigt state.inputString groß an.
        composeTestRule.onNodeWithTag("8").assertExists()

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

        composeTestRule.onNodeWithTag("1").performClick()
        composeTestRule.onNodeWithTag("2").performClick()
        composeTestRule.onNodeWithTag("+").performClick()
        composeTestRule.onNodeWithTag("5").performClick()

        // Jetzt Backspace -> aus "5" wird "0"
        composeTestRule.onNodeWithTag("⌫").performClick() // Nutze das Symbol deines Enums

        // Nochmal Backspace -> Laut deiner Logik sollte jetzt wieder "12" dastehen
        composeTestRule.onNodeWithTag("⌫").performClick()

        composeTestRule.onNodeWithTag("Input")
            .assertIsDisplayed()
            .assertTextEquals("12")

    }
}