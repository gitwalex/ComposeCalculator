package com.gerwalex.calculator.arithmatic

import androidx.lifecycle.ViewModel
import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.common.NumberButtonType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal

class CalculatorBrain : ViewModel() {

    private var isInitailized = false

    // Interner MutableStateFlow
    private val _state = MutableStateFlow(UICalculateState())

    // Externer schreibgeschützter Flow für die UI
    val state: StateFlow<UICalculateState> = _state.asStateFlow()

    fun setup(initialValue: BigDecimal) {
        if (!isInitailized) {
            _state.update { it.copy(input = initialValue) }
            isInitailized = true
        }
    }

    fun onNumberAction(action: NumberButtonType) {
        _state.update { currentState ->
            var workingInput = currentState.inputString

            // Logik für ClearInput-Zustand
            if (currentState.pendingOperation == ActionButtonType.ClearInput) {
                workingInput = "0"
            }

            val newInput = when {
                action == NumberButtonType.BackSpace -> {
                    if (workingInput.length > 1) workingInput.dropLast(1) else "0"
                }

                action == NumberButtonType.Period && workingInput.contains('.') -> return@update currentState
                action == NumberButtonType.Zero && workingInput == "0" -> return@update currentState
                workingInput == "0" -> action.type
                else -> workingInput + action.type
            }

            currentState.copy(input = newInput.toBigDecimal())
        }
    }

    fun onAction(action: ActionButtonType) {
        when (action) {
            ActionButtonType.ClearAll -> onClearAll()
            ActionButtonType.ClearInput -> onClearInput()
            ActionButtonType.BackSpace -> onBackSpace()
            ActionButtonType.Delete -> TODO()
            ActionButtonType.Evaluate -> {
                evaluate()
                _state.update { it.copy(pendingOperation = ActionButtonType.ClearInput) }
            }

            ActionButtonType.ToggleSign -> onToggleSign()
            else -> {
                evaluate()
                _state.update { currentState ->
                    currentState.copy(
                        pendingOperation = action,
                        pendingValue = currentState.input,
                        input = BigDecimal.ZERO,
                    )
                }
            }
        }
    }

    private fun evaluate() {
        val currentOp = _state.value.pendingOperation
        when (currentOp) {
            ActionButtonType.ClearInput -> _state.update { it.copy(pendingValue = BigDecimal.ZERO) }
            ActionButtonType.Add -> onAdd()
            ActionButtonType.Subtract -> onSubtract()
            ActionButtonType.Multiply -> onMultiply()
            ActionButtonType.Divide -> onDivide()
            ActionButtonType.ClearAll -> onClearAll()
            ActionButtonType.BackSpace -> onBackSpace()
            ActionButtonType.Delete -> TODO()
            ActionButtonType.Evaluate -> {
                evaluate()
                _state.update { it.copy(pendingOperation = ActionButtonType.ClearInput) }
            }

            ActionButtonType.ToggleSign -> _state.update { it.copy(pendingValue = -it.pendingValue) }
            ActionButtonType.None -> { /* Nichts zu tun */
            }
        }
    }

    private fun onClearAll() {
        _state.value = UICalculateState()
    }

    private fun onBackSpace() {
        _state.update { currentState ->
            val workingInput = currentState.inputString
            when {
                workingInput == "0" && currentState.pendingOperation != ActionButtonType.None -> {
                    currentState.copy(
                        input = currentState.pendingValue,
                        pendingOperation = ActionButtonType.None,
                        pendingValue = BigDecimal.ZERO
                    )
                }

                workingInput.length == 2 && currentState.input < BigDecimal.ZERO -> {
                    currentState.copy(input = BigDecimal.ZERO)
                }

                workingInput.length > 1 -> {
                    currentState.copy(input = currentState.inputString.dropLast(1).toBigDecimal())
                }

                else -> currentState.copy(input = BigDecimal.ZERO)
            }
        }
    }

    private fun onToggleSign() {
        _state.update { it.copy(input = it.input.negate()) }
    }

    private fun onClearInput() {
        _state.update { it.copy(input = BigDecimal.ZERO) }
    }

    private fun onAdd() {
        _state.update { s ->
            s.copy(
                input = (s.pendingValue.add(s.input)).stripTrailingZeros(),
                pendingOperation = ActionButtonType.None
            )
        }
    }

    private fun onSubtract() {
        _state.update { s ->
            s.copy(
                input = (s.pendingValue.minus(s.input)).stripTrailingZeros(),
                pendingOperation = ActionButtonType.None
            )
        }
    }

    private fun onMultiply() {
        _state.update { s ->
            s.copy(
                input = (s.pendingValue.multiply(s.input)).stripTrailingZeros(),
                pendingOperation = ActionButtonType.None
            )
        }
    }

    private fun onDivide() {
        _state.update { s ->
            s.copy(
                input = s.pendingValue.divide(s.input).stripTrailingZeros(),
                pendingOperation = ActionButtonType.None
            )
        }
    }
}