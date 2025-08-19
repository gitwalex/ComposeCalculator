package com.gerwalex.calculator.arithmatic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.common.NumberButtonType
import java.math.BigDecimal

class CalculateViewModel : ViewModel() {


    var state by mutableStateOf(UICalculateState())
    fun onAction(action: NumberButtonType) {
        state = state.copy(input = state.input + action.type)
    }

    fun onAction(action: ActionButtonType) {
        executePendingOperation()
        when (action) {
            ActionButtonType.ClearAll -> onClearInput()
            ActionButtonType.Delete -> TODO()
            ActionButtonType.Evaluate -> executePendingOperation()
            ActionButtonType.ClearInput -> TODO()
            ActionButtonType.ToggleSign -> onToggleSign()
            else -> state = state.copy(
                pendingOperation = action,
                currentValue = state.input.toBigDecimal(),
                input = "",
            )
        }
    }

    private fun executePendingOperation() {
        when (state.pendingOperation) {
            ActionButtonType.Add -> onAdd()
            ActionButtonType.Subtract -> onSubtract()
            ActionButtonType.Multiply -> onMultiply()
            ActionButtonType.Divide -> onDivide()
            ActionButtonType.Ignore -> {
                return /*Ignore*/
            }

            else -> {
                throw IllegalStateException("Invalid operation")
            }
        }
        state = state.copy(pendingOperation = ActionButtonType.Ignore)
    }

    private fun onToggleSign() {
        if (state.pendingOperation == ActionButtonType.Ignore)
            state.copy(currentValue = state.currentValue.negate())
        else
            state.copy(toggleSign = !state.toggleSign)
    }


    private fun onClearInput() {
        state = state.copy(currentValue = BigDecimal.ZERO)
    }

    private fun onAdd() {
        state =
            state.copy(
                currentValue = state.currentValue + state.currentInput
            )
    }

    private fun onSubtract() {
        state =
            state.copy(
                currentValue = state.currentValue - state.currentInput
            )
    }

    private fun onMultiply() {
        state =
            state.copy(
                currentValue = state.currentValue * state.currentInput
            )
    }

    private fun onDivide() {
        state =
            state.copy(
                currentValue = state.currentValue / state.currentInput
            )
    }


}
