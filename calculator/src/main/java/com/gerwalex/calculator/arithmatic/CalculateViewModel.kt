package com.gerwalex.calculator.arithmatic

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.common.NumberButtonType

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
        Log.d(
            "NumberButtonAction",
            "onAction: input=${state.input} - value = ${state.currentInput}"
        )
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
        state = state.copy(pendingOperation = ActionButtonType.None)
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
        state = state.copy(input = "")
    }

    private fun onAdd() {
        state =
            state.copy(
                input = (state.pendingValue + state.currentInput).stripTrailingZeros().toString()
            )
    }

    private fun onSubtract() {
        state =
            state.copy(
                input = (state.pendingValue - state.currentInput).stripTrailingZeros().toString()
            )
    }

    private fun onMultiply() {
        state =
            state.copy(
                input = (state.pendingValue * state.currentInput).stripTrailingZeros().toString()
            )
    }

    private fun onDivide() {
        state =
            state.copy(
                input = (state.pendingValue / state.currentInput).stripTrailingZeros().toString()
            )
    }


}
