package com.gerwalex.calculator.arithmatic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.common.NumberButtonType
import java.math.RoundingMode

class CalculateViewModel : ViewModel() {


    var state by mutableStateOf(UICalculateState())
    fun onAction(action: NumberButtonType) {
        val input = when {
            action == NumberButtonType.Period && state.input.contains('.') -> return
            action == NumberButtonType.Zero && state.input == "0" -> return
            state.input == "0" -> action.type
            else -> state.input + action.type
        }
        state = state.copy(input = input)
    }


    fun onAction(action: ActionButtonType) {
        executePendingOperation()
        when (action) {
            ActionButtonType.ClearAll -> onClearAll()
            ActionButtonType.ClearInput -> onClearInput()
            ActionButtonType.BackSpace -> onBackSpace()
            ActionButtonType.Delete -> TODO()
            ActionButtonType.Evaluate -> executePendingOperation()
            ActionButtonType.ToggleSign -> onToggleSign()
            else -> state = state.copy(
                pendingOperation = action,
                pendingValue = state.currentInput,
                input = "0",
            )
        }
    }

    private fun executePendingOperation() {
        when (state.pendingOperation) {
            ActionButtonType.None -> return // Ignore
            ActionButtonType.Add -> onAdd()
            ActionButtonType.Subtract -> onSubtract()
            ActionButtonType.Multiply -> onMultiply()
            ActionButtonType.Divide -> onDivide()

            else -> {
                throw IllegalStateException("Invalid operation ${state.pendingOperation}")
            }
        }
    }

    private fun onClearAll() {
        state = UICalculateState()
    }

    private fun onBackSpace() {
        state = when {
            state.input.length > 1 -> {
                state.copy(input = state.input.dropLast(1))
            }

            state.input.length == 1 -> {
                state.copy(input = "0", pendingOperation = ActionButtonType.None)
            }

            else -> state
        }
    }

    private fun onToggleSign() {
        if (state.pendingOperation == ActionButtonType.None)
            state.copy(pendingValue = state.pendingValue.negate())
        else
            state.copy(toggleSign = !state.toggleSign)
    }


    private fun onClearInput() {
        state = state.copy(input = "0")
    }

    private fun onAdd() {
        state =
            state.copy(
                input = (state.pendingValue + state.currentInput).stripTrailingZeros().toString(),
                pendingOperation = ActionButtonType.None
            )
    }

    private fun onSubtract() {
        state =
            state.copy(
                input = (state.pendingValue - state.currentInput).stripTrailingZeros().toString(),
                pendingOperation = ActionButtonType.None
            )
    }

    private fun onMultiply() {
        state =
            state.copy(
                input = state.pendingValue
                    .multiply(state.currentInput)
                    .stripTrailingZeros().toString(),
                pendingOperation = ActionButtonType.None
            )
    }

    private fun onDivide() {
        state =
            state.copy(
                input = (state.pendingValue
                    .divide(state.currentInput, 8, RoundingMode.HALF_UP))
                    .stripTrailingZeros().toString(),
                pendingOperation = ActionButtonType.None
            )
    }


}
