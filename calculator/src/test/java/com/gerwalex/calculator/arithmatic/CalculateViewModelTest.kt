package com.gerwalex.calculator.arithmatic

import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.common.NumberButtonType
import org.junit.Test
import java.math.BigDecimal

class CalculateViewModelTest {
    private val viewModel: CalculateViewModel = CalculateViewModel()

    @Test
    fun testOnActionAdd() {
        viewModel.onAction(ActionButtonType.Add)
        assert(viewModel.state.pendingOperation == ActionButtonType.Add)
    }

    @Test
    fun testOnActionNumber() {
        viewModel.onAction(NumberButtonType.One)
        viewModel.onAction(NumberButtonType.Two)
        viewModel.onAction(NumberButtonType.Period)
        viewModel.onAction(NumberButtonType.Three)
        viewModel.onAction(NumberButtonType.Four)
        assert(viewModel.state.input == "12.34")
        assert(viewModel.state.currentInput == "12.34".toBigDecimal()) { "Invalid input " }
    }

    @Test
    fun testOnActionAddNumbers() {
        viewModel.onAction(NumberButtonType.One)
        viewModel.onAction(NumberButtonType.Two)
        viewModel.onAction(NumberButtonType.Period)
        viewModel.onAction(NumberButtonType.Three)
        viewModel.onAction(NumberButtonType.Four)
        assert(viewModel.state.input == "12.34")
        assert(viewModel.state.currentInput == "12.34".toBigDecimal()) { "Invalid input " }
        viewModel.onAction(ActionButtonType.Add)
        assert(viewModel.state.pendingOperation == ActionButtonType.Add)
        assert(viewModel.state.input == "0") { "Invalid input" }
        assert(viewModel.state.currentInput == BigDecimal.ZERO) { "Invalid current input" }
        assert(viewModel.state.pendingValue == "12.34".toBigDecimal()) { "Invalid pending Value" }
        viewModel.onAction(NumberButtonType.Five)
        viewModel.onAction(NumberButtonType.Period)
        viewModel.onAction(NumberButtonType.Six)
        viewModel.onAction(ActionButtonType.Evaluate)
        assert(viewModel.state.input == "17.94") { "Invalid input, input is ${viewModel.state.input}" }

    }
    @Test
    fun testOnActionMultiplyNumbers() {
        viewModel.onAction(NumberButtonType.One)
        viewModel.onAction(NumberButtonType.Period)
        viewModel.onAction(NumberButtonType.Two)
        assert(viewModel.state.input == "1.2")
        assert(viewModel.state.currentInput == "1.2".toBigDecimal()) { "Invalid input " }
        viewModel.onAction(ActionButtonType.Multiply)
        assert(viewModel.state.pendingOperation == ActionButtonType.Multiply)
        assert(viewModel.state.input == "0") { "Invalid input" }
        assert(viewModel.state.currentInput == BigDecimal.ZERO) { "Invalid current input" }
        assert(viewModel.state.pendingValue == "1.2".toBigDecimal()) { "Invalid pending Value" }
        viewModel.onAction(NumberButtonType.Five)
        viewModel.onAction(ActionButtonType.Evaluate)
        assert(viewModel.state.input == "6") { "Invalid input, input is ${viewModel.state.input}" }
    }
    @Test
    fun testOnActionDivideNumbers() {
        viewModel.onAction(NumberButtonType.One)
        viewModel.onAction(NumberButtonType.Period)
        assert(viewModel.state.input == "1.")
        assert(viewModel.state.currentInput == "1".toBigDecimal()) { "Invalid input " }
        viewModel.onAction(ActionButtonType.Divide)
        assert(viewModel.state.pendingOperation == ActionButtonType.Divide)
        assert(viewModel.state.input == "0") { "Invalid input" }
        assert(viewModel.state.currentInput == BigDecimal.ZERO) { "Invalid current input" }
        assert(viewModel.state.pendingValue == "1".toBigDecimal()) { "Invalid pending Value" }
        viewModel.onAction(NumberButtonType.Five)
        viewModel.onAction(ActionButtonType.Evaluate)
        assert(viewModel.state.input == "0.2") { "Invalid input, input is ${viewModel.state.input}" }
    }

}