package com.gerwalex.calculator.arithmatic
import androidx.lifecycle.ViewModel
import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.common.NumberButtonType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorBrain : ViewModel() {

    // Interner MutableStateFlow
    private val _state = MutableStateFlow(UICalculateState())

    // Externer schreibgeschützter Flow für die UI
    val state: StateFlow<UICalculateState> = _state.asStateFlow()

    fun onAction(action: NumberButtonType) {
        _state.update { currentState ->
            var workingInput = currentState.input

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

            currentState.copy(input = newInput)
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
                        pendingValue = currentState.currentInput,
                        input = "0",
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
            when {
                currentState.input == "0" && currentState.pendingOperation != ActionButtonType.None -> {
                    currentState.copy(
                        input = currentState.pendingMemory,
                        pendingOperation = ActionButtonType.None,
                        pendingValue = BigDecimal.ZERO
                    )
                }

                currentState.input.length == 2 && currentState.currentInput < BigDecimal.ZERO -> {
                    currentState.copy(input = "0")
                }

                currentState.input.length > 1 -> {
                    currentState.copy(input = currentState.input.dropLast(1))
                }

                else -> currentState.copy(input = "0")
            }
        }
    }

    private fun onToggleSign() {
        _state.update { it.copy(input = it.currentInput.negate().toString()) }
    }

    private fun onClearInput() {
        _state.update { it.copy(input = "0") }
    }

    private fun onAdd() {
        _state.update { s ->
            s.copy(
                input = (s.pendingValue + s.currentInput).stripTrailingZeros().toString(),
                pendingOperation = ActionButtonType.None
            )
        }
    }

    private fun onSubtract() {
        _state.update { s ->
            s.copy(
                input = (s.pendingValue - s.currentInput).stripTrailingZeros().toString(),
                pendingOperation = ActionButtonType.None
            )
        }
    }

    private fun onMultiply() {
        _state.update { s ->
            s.copy(
                input = s.pendingValue.multiply(s.currentInput).stripTrailingZeros().toString(),
                pendingOperation = ActionButtonType.None
            )
        }
    }

    private fun onDivide() {
        _state.update { s ->
            val result = s.pendingValue.divide(s.currentInput, 8, RoundingMode.HALF_UP)
            s.copy(
                input = result.stripTrailingZeros().toString(),
                pendingOperation = ActionButtonType.None
            )
        }
    }
}