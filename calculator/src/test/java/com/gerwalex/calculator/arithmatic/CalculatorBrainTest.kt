package com.gerwalex.calculator.arithmatic

import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.common.NumberButtonType
import org.junit.Test
import java.math.BigDecimal

class CalculatorBrainTest {
    private val brain: CalculatorBrain = CalculatorBrain()


    @Test
    fun testOnActionAdd() {
        brain.onAction(ActionButtonType.Add)
        assert(brain.state.pendingOperation == ActionButtonType.Add)
    }

    @Test
    fun inputValueOne() {
        brain.onAction(NumberButtonType.One)
        brain.onAction(NumberButtonType.Two)
        brain.onAction(NumberButtonType.Period)
        brain.onAction(NumberButtonType.Three)
        brain.onAction(NumberButtonType.Four)
        assert(brain.state.input == "12.34")
        assert(brain.state.currentInput == "12.34".toBigDecimal()) { "Invalid input " }

    }

    @Test
    fun inputValueTwo() {
        with(brain) {
            onAction(NumberButtonType.Five)
            onAction(NumberButtonType.Period)
            onAction(NumberButtonType.Six)
            assert(state.input == "5.6") { "Invalid input ${state.input}" }
            assert(state.currentInput == "5.6".toBigDecimal()) { "Invalid input ${state.currentInput}" }

        }
    }

    @Test
    fun testOnActionAddNumbers() {
        with(brain) {
            inputValueOne()
            onAction(ActionButtonType.Add)
            assert(state.pendingOperation == ActionButtonType.Add)
            assert(state.input == "0") { "Invalid input ${state.input}" }
            assert(state.pendingValue == "12.34".toBigDecimal()) { "Invalid pending Value ${state.pendingValue}" }
            inputValueTwo()
            onAction(ActionButtonType.Evaluate)
            assert(state.input == "17.94") { "Invalid input, input is ${state.input}" }
        }
    }

    @Test
    fun testOnActionMultiplyNumbers() {
        with(brain) {
            inputValueOne()
            onAction(ActionButtonType.Multiply)
            assert(state.pendingOperation == ActionButtonType.Multiply)
            assert(state.input == "0") { "Invalid input ${state.input}" }
            assert(state.currentInput == BigDecimal.ZERO) { "Invalid current input ${state.currentInput}" }
            assert(state.pendingValue == "12.34".toBigDecimal()) { "Invalid pending Value ${state.pendingValue}" }
            inputValueTwo()
            onAction(ActionButtonType.Evaluate)
            assert(state.input == "69.104") { "Invalid input, input is ${state.input}" }
        }
    }

    @Test
    fun testOnActionDivideNumbers() {
        with(brain) {
            onAction(NumberButtonType.One)
            onAction(NumberButtonType.Period)
            assert(state.input == "1.")
            assert(state.currentInput == "1".toBigDecimal()) { "Invalid input ${state.currentInput}" }
            onAction(ActionButtonType.Divide)
            assert(state.pendingOperation == ActionButtonType.Divide)
            assert(state.input == "0") { "Invalid input ${state.input}" }
            assert(state.currentInput == BigDecimal.ZERO) { "Invalid current input ${state.currentInput}" }
            assert(state.pendingValue == "1".toBigDecimal()) { "Invalid pending Value $state.pendingValue" }
            onAction(NumberButtonType.Five)
            onAction(ActionButtonType.Evaluate)
            assert(state.input == "0.2") { "Invalid input, input is ${state.input}" }
            onAction(NumberButtonType.Two)
            assert(state.input == "2") { "Invalid input, input is ${state.input}" }
        }
    }

    @Test
    fun testTwoDifferentCalculations() {
        with(brain) {
            onAction(NumberButtonType.One)
            onAction(ActionButtonType.Add)
            assert(state.pendingOperation == ActionButtonType.Add)
            onAction(NumberButtonType.Five)
            onAction(ActionButtonType.Evaluate)
            assert(state.pendingOperation == ActionButtonType.ClearInput) { "Invalid current input ${state.currentInput}" }
            assert(state.input == "6") { "Invalid input, input is ${state.input}" }
            onAction(NumberButtonType.Two)
            assert(state.input == "2") { "Invalid input, input is ${state.input}" }
        }
    }

    @Test
    fun testBackSpace() {
        with(brain) {
            inputValueOne()
            onAction(ActionButtonType.Add)
            assert(state.pendingOperation == ActionButtonType.Add)
            assert(state.input == "0") { "Invalid input ${state.input}" }
            assert(state.pendingMemory == "12.34")
            inputValueTwo()
            assert(state.input == "5.6") { "Invalid input, input is ${state.input}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.input == "5.") { "Invalid input, input is ${state.input}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.input == "5") { "Invalid input, input is ${state.input}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.input == "0") { "Invalid input, input is ${state.input}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.input == "12.34") { "Invalid input, input is ${state.input}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.input == "12.3") { "Invalid input, input is ${state.input}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.input == "12.") { "Invalid input, input is ${state.input}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.input == "12") { "Invalid input, input is ${state.input}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.input == "1") { "Invalid input, input is ${state.input}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.input == "0") { "Invalid input, input is ${state.input}" }
            onAction(ActionButtonType.BackSpace)
            assert(state.input == "0") { "Invalid input, input is ${state.input}" }
        }
    }

    @Test
    fun testBackspace2WithToggleSign() {
        with(brain) {
            inputValueOne()
            onAction(ActionButtonType.ToggleSign)
            assert(state.input == "-12.34")
            onAction(ActionButtonType.BackSpace)
            assert(state.input == "-12.3")
            onAction(ActionButtonType.BackSpace)
            assert(state.input == "-12.")
            onAction(ActionButtonType.BackSpace)
            assert(state.input == "-12")
            onAction(ActionButtonType.BackSpace)
            assert(state.input == "-1")
            onAction(ActionButtonType.BackSpace)
            assert(state.input == "0")
        }
    }

    @Test
    fun toggleSign() {
        with(brain) {
            inputValueOne()
            onAction(ActionButtonType.ToggleSign)
            assert(state.input == "-12.34")
        }
    }
}
