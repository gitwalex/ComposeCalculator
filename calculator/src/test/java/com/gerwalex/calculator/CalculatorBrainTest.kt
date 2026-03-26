package com.gerwalex.calculator

import com.gerwalex.calculator.arithmatic.CalculatorBrain
import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.common.NumberButtonType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

class CalculatorBrainTest {
    private lateinit var brain: CalculatorBrain
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        brain = CalculatorBrain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test initial setup sets value`() = runTest {
        val initial = BigDecimal("10.5")
        val settings = CalculatorSettings(initialValue = initial)
        brain.setup(settings)

        assertEquals(initial, brain.state.first().input)
    }

    @Test
    fun `test addition 5 + 3 = 8`() = runTest {
        // Tippe 5
        brain.onNumberAction(NumberButtonType.Five)
        // Drücke Plus
        brain.onAction(ActionButtonType.Add)
        // Tippe 3
        brain.onNumberAction(NumberButtonType.Three)
        // Drücke Gleich
        brain.onAction(ActionButtonType.Evaluate)

        assertEquals(BigDecimal("8"), brain.state.value.input)
    }

    @Test
    fun `test subtraction 10 - 4 = 6`() = runTest {
        // Tippe 10 (Eins dann Null)
        brain.onNumberAction(NumberButtonType.One)
        brain.onNumberAction(NumberButtonType.Zero)

        brain.onAction(ActionButtonType.Subtract)

        brain.onNumberAction(NumberButtonType.Four)
        brain.onAction(ActionButtonType.Evaluate)

        assertEquals(BigDecimal("6"), brain.state.value.input)
    }

    @Test
    fun `test decimal input logic`() = runTest {
        // Tippe 5 . 2
        brain.onNumberAction(NumberButtonType.Five)
        brain.onNumberAction(NumberButtonType.Period)
        brain.onNumberAction(NumberButtonType.Two)

        // Nochmal Punkt sollte ignoriert werden (laut deiner Logik)
        brain.onNumberAction(NumberButtonType.Period)

        assertEquals(BigDecimal("5.2"), brain.state.value.input)
    }

    @Test
    fun `test toggle sign`() = runTest {
        brain.onNumberAction(NumberButtonType.Nine)
        brain.onAction(ActionButtonType.ToggleSign)

        assertEquals(BigDecimal("-9"), brain.state.value.input)

        brain.onAction(ActionButtonType.ToggleSign)
        assertEquals(BigDecimal("9"), brain.state.value.input)
    }

    @Test
    fun `test clear all resets state`() = runTest {
        brain.onNumberAction(NumberButtonType.Five)
        brain.onAction(ActionButtonType.Add)
        brain.onAction(ActionButtonType.ClearAll)

        val state = brain.state.value
        assertEquals(BigDecimal.ZERO, state.input)
        assertEquals(ActionButtonType.None, state.pendingOperation)
    }

    @Test
    fun `test backspace on single digit`() = runTest {
        brain.onNumberAction(NumberButtonType.Five)
        brain.onNumberAction(NumberButtonType.BackSpace)

        assertEquals(BigDecimal.ZERO, brain.state.value.input)
    }

    @Test
    fun `test chain operations 5 + 5 + 5`() = runTest {
        brain.onNumberAction(NumberButtonType.Five)
        brain.onAction(ActionButtonType.Add)

        brain.onNumberAction(NumberButtonType.Five)
        brain.onAction(ActionButtonType.Add) // Hier passiert die Zwischenrechnung 5+5

        // Check Zwischenstand: Ergebnis der ersten Rechnung muss im Speicher (pendingValue) sein
        assertEquals(BigDecimal("10"), brain.state.value.pendingValue)
        assertEquals("10", brain.state.value.inputString)

        brain.onNumberAction(NumberButtonType.Five)
        brain.onAction(ActionButtonType.Evaluate)

        assertEquals("15", brain.state.value.inputString)
    }


    @Test
    fun testOnActionAdd() {
        brain.onAction(ActionButtonType.Add)
        assert(brain.state.value.pendingOperation == ActionButtonType.Add)
    }

    @Test
    fun inputValueOne() {
        brain.onNumberAction(NumberButtonType.One)
        brain.onNumberAction(NumberButtonType.Two)
        brain.onNumberAction(NumberButtonType.Period)
        brain.onNumberAction(NumberButtonType.Three)
        brain.onNumberAction(NumberButtonType.Four)
        assert(brain.state.value.inputString == "12.34")
        assert(brain.state.value.input == "12.34".toBigDecimal()) { "Invalid input " }

    }

    @Test
    fun inputValueTwo() {
        with(brain) {
            onNumberAction(NumberButtonType.Five)
            onNumberAction(NumberButtonType.Period)
            onNumberAction(NumberButtonType.Six)
            assert(state.value.inputString == "5.6") { "Invalid input ${state.value.inputString}" }
            assert(state.value.input == "5.6".toBigDecimal()) { "Invalid input ${state.value.input}" }

        }
    }

    @Test
    fun testOnActionAddNumbers() {
        with(brain) {
            inputValueOne()
            onAction(ActionButtonType.Add)
            assert(state.value.pendingOperation == ActionButtonType.Add)
            assert(state.value.inputString == "12.34") { "Invalid input ${state.value.inputString}" }
            assert(state.value.pendingValue == "12.34".toBigDecimal()) { "Invalid pending Value ${state.value.pendingValue}" }
            inputValueTwo()
            onAction(ActionButtonType.Evaluate)
            assert(state.value.inputString == "17.94") { "Invalid input, input is ${state.value.inputString}" }
        }
    }

    @Test
    fun testOnActionMultiplyNumbers() {
        with(brain) {
            inputValueOne()
            onAction(ActionButtonType.Multiply)
            assert(state.value.pendingOperation == ActionButtonType.Multiply)
            assert(state.value.inputString == "12.34") { "Invalid input ${state.value.inputString}" }
            assert(state.value.pendingValue == "12.34".toBigDecimal()) { "Invalid pending Value ${state.value.pendingValue}" }
            inputValueTwo()
            onAction(ActionButtonType.Evaluate)
            assert(state.value.inputString == "69.104") { "Invalid input, input is ${state.value.inputString}" }
        }
    }

    @Test
    fun testOnActionDivideNumbers() {
        with(brain) {
            onNumberAction(NumberButtonType.One)
            onNumberAction(NumberButtonType.Period)
            assert(state.value.inputString == "1.") { "Invalid input ${state.value.inputString}" }
            onAction(ActionButtonType.Divide)
            assert(state.value.pendingOperation == ActionButtonType.Divide)
            assert(state.value.inputString == "1.") { "Invalid input ${state.value.inputString}" }
            assert(state.value.pendingValue == "1".toBigDecimal()) { "Invalid pending Value $state.pendingValue" }
            onNumberAction(NumberButtonType.Five)
            onAction(ActionButtonType.Evaluate)
            assert(state.value.inputString == "0.2") { "Invalid input, input is ${state.value.inputString}" }
            onNumberAction(NumberButtonType.Two)
            assert(state.value.inputString == "2") { "Invalid input, input is ${state.value.inputString}" }
        }
    }

    @Test
    fun testTwoDifferentCalculations() {
        with(brain) {
            onNumberAction(NumberButtonType.One)
            onAction(ActionButtonType.Add)
            assert(state.value.pendingOperation == ActionButtonType.Add)
            onNumberAction(NumberButtonType.Five)
            onAction(ActionButtonType.Evaluate)
            assertEquals(state.value.pendingOperation, ActionButtonType.None)
            assertEquals(state.value.inputString, "6")
            onNumberAction(NumberButtonType.Two)
            assertEquals(state.value.inputString, "2")
        }
    }

    @Test
    fun testBackSpace() {
        with(brain) {
            inputValueOne()
            onAction(ActionButtonType.Add)
            assert(state.value.pendingOperation == ActionButtonType.Add)
            assert(state.value.inputString == "12.34") { "Invalid input ${state.value.inputString}" }
            assert(state.value.pendingMemory == "12.34")
            inputValueTwo()
            assert(state.value.inputString == "5.6") { "Invalid input, input is ${state.value.inputString}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.value.inputString == "5.") { "Invalid input, input is ${state.value.inputString}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.value.inputString == "5") { "Invalid input, input is ${state.value.inputString}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.value.inputString == "0") { "Invalid input, input is ${state.value.inputString}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.value.inputString == "12.34") { "Invalid input, input is ${state.value.inputString}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.value.inputString == "12.3") { "Invalid input, input is ${state.value.inputString}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.value.inputString == "12.") { "Invalid input, input is ${state.value.inputString}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.value.inputString == "12") { "Invalid input, input is ${state.value.inputString}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.value.inputString == "1") { "Invalid input, input is ${state.value.inputString}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.value.inputString == "0") { "Invalid input, input is ${state.value.inputString}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.value.inputString == "0") { "Invalid input, input is ${state.value.inputString}" }
        }
    }

    @Test
    fun testBackspace2WithToggleSign() {
        with(brain) {
            inputValueOne()
            onAction(ActionButtonType.ToggleSign)
            assert(state.value.inputString == "-12.34")
            onAction(ActionButtonType.BackSpace)
            assert(state.value.inputString == "-12.3")
            onAction(ActionButtonType.BackSpace)
            assert(state.value.inputString == "-12.")
            onAction(ActionButtonType.BackSpace)
            assert(state.value.inputString == "-12")
            onAction(ActionButtonType.BackSpace)
            assert(state.value.inputString == "-1")
            onAction(ActionButtonType.BackSpace)
            assert(state.value.inputString == "0")
        }
    }

    @Test
    fun toggleSign() {
        with(brain) {
            inputValueOne()
            onAction(ActionButtonType.ToggleSign)
            assert(state.value.inputString == "-12.34")
        }
    }

    @Test
    fun `test division by zero returns zero`() = runTest {
        brain.onNumberAction(NumberButtonType.Five)
        brain.onAction(ActionButtonType.Divide)
        brain.onNumberAction(NumberButtonType.Zero)
        brain.onAction(ActionButtonType.Evaluate)

        assertEquals(BigDecimal.ZERO, brain.state.value.input)
        // Optional: Prüfen ob ein Fehlerflag im State gesetzt wurde, falls du das einbaust
    }

    @Test
    fun `test multiple evaluate calls`() = runTest {
        brain.onNumberAction(NumberButtonType.Five)
        brain.onAction(ActionButtonType.Add)
        brain.onNumberAction(NumberButtonType.Two)
        brain.onAction(ActionButtonType.Evaluate) // Ergebnis 7
        brain.onAction(ActionButtonType.Evaluate) // Sollte stabil bei 7 bleiben (oder +2 rechnen, je nach Design)

        assertEquals("7", brain.state.value.inputString)
    }

    @Test
    fun `test leading zeros and starting with period`() = runTest {
        // Start mit Punkt
        brain.onNumberAction(NumberButtonType.Period)
        assertEquals("0.", brain.state.value.inputString)

        brain.onAction(ActionButtonType.ClearAll)

        // Mehrere Nullen am Anfang
        brain.onNumberAction(NumberButtonType.Zero)
        brain.onNumberAction(NumberButtonType.Zero)
        brain.onNumberAction(NumberButtonType.Five)
        assertEquals("5", brain.state.value.inputString)
    }

    @Test
    fun `test changing operator before second number`() = runTest {
        brain.onNumberAction(NumberButtonType.Five)
        brain.onAction(ActionButtonType.Add)
        brain.onAction(ActionButtonType.Subtract) // User korrigiert sich

        brain.onNumberAction(NumberButtonType.Two)
        brain.onAction(ActionButtonType.Evaluate)

        assertEquals(BigDecimal("3"), brain.state.value.input)
    }

    @Test
    fun `test high precision division and stripping zeros`() = runTest {
        brain.onNumberAction(NumberButtonType.One)
        brain.onAction(ActionButtonType.Divide)
        brain.onNumberAction(NumberButtonType.Three)
        brain.onAction(ActionButtonType.Evaluate)

        // Erwartet: 0.33333333 (8 Stellen laut deinem Code)
        assertEquals("0.33333333", brain.state.value.inputString)

        brain.onAction(ActionButtonType.ClearAll)

        // Test: 1 / 2 soll 0.5 sein, nicht 0.50000000
        brain.onNumberAction(NumberButtonType.One)
        brain.onAction(ActionButtonType.Divide)
        brain.onNumberAction(NumberButtonType.Two)
        brain.onAction(ActionButtonType.Evaluate)

        assertEquals("0.5", brain.state.value.inputString)
    }

    @Test
    fun `test continuing calculation after evaluate`() = runTest {
        brain.onNumberAction(NumberButtonType.Five)
        brain.onAction(ActionButtonType.Add)
        brain.onNumberAction(NumberButtonType.Five)
        brain.onAction(ActionButtonType.Evaluate) // Ergebnis 10, pendingOperation ist ClearInput

        // Jetzt direkt weiterrechnen
        brain.onAction(ActionButtonType.Divide)
        brain.onNumberAction(NumberButtonType.Two)
        brain.onAction(ActionButtonType.Evaluate)

        assertEquals(BigDecimal("5"), brain.state.value.input)
    }

    @Test
    fun `test starting new decimal number after evaluate`() = runTest {
        brain.onNumberAction(NumberButtonType.Five)
        brain.onAction(ActionButtonType.Evaluate) // "5"

        brain.onNumberAction(NumberButtonType.Period)
        brain.onNumberAction(NumberButtonType.Two)

        assertEquals("0.2", brain.state.value.inputString)
    }

    @Test
    fun `test toggle sign on zero stays zero`() = runTest {
        brain.onNumberAction(NumberButtonType.Zero)
        brain.onAction(ActionButtonType.ToggleSign)

        // Es sollte "0" bleiben, nicht "-0" (außer du willst das explizit so)
        assertEquals("0", brain.state.value.inputString)
    }

    @Test
    fun `test operator after trailing decimal point`() = runTest {
        brain.onNumberAction(NumberButtonType.Five)
        brain.onNumberAction(NumberButtonType.Period) // Display zeigt "5."

        // Darf nicht abstürzen und muss "5" als Wert nehmen
        brain.onAction(ActionButtonType.Add)
        brain.onNumberAction(NumberButtonType.Two)
        brain.onAction(ActionButtonType.Evaluate)

        assertEquals(BigDecimal("7"), brain.state.value.input)
    }

    @Test
    fun `test multiple zeros after decimal point`() = runTest {
        brain.onNumberAction(NumberButtonType.Zero)
        brain.onNumberAction(NumberButtonType.Period)
        brain.onNumberAction(NumberButtonType.Zero)
        brain.onNumberAction(NumberButtonType.Zero)
        brain.onNumberAction(NumberButtonType.Five)

        assertEquals("0.005", brain.state.value.inputString)
        assertEquals(BigDecimal("0.005"), brain.state.value.input)
    }

    @Test
    fun `test multiple zeros before decimal point`() = runTest {
        brain.onNumberAction(NumberButtonType.Zero)
        brain.onNumberAction(NumberButtonType.Zero)
        brain.onNumberAction(NumberButtonType.Zero)
        brain.onNumberAction(NumberButtonType.Five)

        assertEquals("5", brain.state.value.inputString)
        assertEquals(BigDecimal("5"), brain.state.value.input)
    }

    @Test
    fun `test backspace with multiple decimal zeros`() = runTest {
        brain.onNumberAction(NumberButtonType.Zero)
        brain.onNumberAction(NumberButtonType.Period)
        brain.onNumberAction(NumberButtonType.Zero)
        brain.onNumberAction(NumberButtonType.Zero) // "0.00"

        brain.onAction(ActionButtonType.BackSpace)
        assertEquals("0.0", brain.state.value.inputString)

        brain.onAction(ActionButtonType.BackSpace)
        assertEquals("0.", brain.state.value.inputString)
    }
}