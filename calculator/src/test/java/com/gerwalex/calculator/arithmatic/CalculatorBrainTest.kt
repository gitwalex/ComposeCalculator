package com.gerwalex.calculator.arithmatic

import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.common.NumberButtonType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        brain.setup(initial)

        assertEquals(initial, brain.state.value.input)
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
        brain.onAction(ActionButtonType.Add) // input wird 0, pending wird 5
        brain.onNumberAction(NumberButtonType.Five)
        brain.onAction(ActionButtonType.Add) // Hier sollte add() ausgeführt werden -> input 10

        assertEquals(BigDecimal("10"), brain.state.value.input)

        brain.onNumberAction(NumberButtonType.Five)
        brain.onAction(ActionButtonType.Evaluate) // 10 + 5

        assertEquals(BigDecimal("15"), brain.state.value.input)
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
            assert(state.value.inputString == "0") { "Invalid input ${state.value.inputString}" }
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
            assert(state.value.inputString == "0") { "Invalid input ${state.value.inputString}" }
            assert(state.value.input == BigDecimal.ZERO) { "Invalid current input ${state.value.input}" }
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
            assert(state.value.inputString == "1.")
            assert(state.value.input == "1".toBigDecimal()) { "Invalid input ${state.value.input}" }
            onAction(ActionButtonType.Divide)
            assert(state.value.pendingOperation == ActionButtonType.Divide)
            assert(state.value.inputString == "0") { "Invalid input ${state.value.inputString}" }
            assert(state.value.input == BigDecimal.ZERO) { "Invalid current input ${state.value.input}" }
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
            assert(state.value.pendingOperation == ActionButtonType.ClearInput) { "Invalid current input ${state.value.input}" }
            assert(state.value.inputString == "6") { "Invalid input, input is ${state.value.inputString}" }
            onNumberAction(NumberButtonType.Two)
            assert(state.value.inputString == "2") { "Invalid input, input is ${state.value.inputString}" }
        }
    }

    @Test
    fun testBackSpace() {
        with(brain) {
            inputValueOne()
            onAction(ActionButtonType.Add)
            assert(state.value.pendingOperation == ActionButtonType.Add)
            assert(state.value.inputString == "0") { "Invalid input ${state.value.inputString}" }
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
}
