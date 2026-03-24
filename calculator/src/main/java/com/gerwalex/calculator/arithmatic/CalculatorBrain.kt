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
            _state.update { it.copy(inputString = initialValue.toPlainString()) }
            isInitailized = true
        }
    }

    /**
     * Processes numerical input and formatting actions such as digits, decimal points, and backspaces.
     *
     * This function updates the current input string in the [state], handling logic for:
     * - Appending digits to the current input.
     * - Preventing multiple decimal points in a single number.
     * - Managing leading zeros to ensure valid numerical representation.
     * - Handling backspace operations to delete the last character or reset to "0".
     * - Resetting the input buffer if a new number entry is starting after an operation.
     *
     * @param action The specific [NumberButtonType] (e.g., Zero-Nine, Period, or BackSpace) triggered by the user.
     */
    fun onNumberAction(action: NumberButtonType) {
        _state.update { currentState ->
            var workingInput =
                if (currentState.isNewInputStarting) "0" else currentState.inputString
            // Logik für ClearInput-Zustand
            if (currentState.pendingOperation == ActionButtonType.ClearInput) {
                workingInput = "0"
            }
            if (action == NumberButtonType.Period && workingInput.contains('.')) return@update currentState
            if (action == NumberButtonType.Zero && workingInput == "0") return@update currentState

            val newInput =
                when {
                    action == NumberButtonType.Period -> workingInput + action.type
                    action == NumberButtonType.BackSpace -> {
                        if (workingInput.length > 1) workingInput.dropLast(1) else "0"
                    }

                    workingInput == "0" -> action.type
                    else -> workingInput + action.type
                }

            currentState.copy(inputString = newInput, isNewInputStarting = false)
        }
    }

    /**
     * Processes non-numeric calculator actions and manages the calculation state.
     *
     * This function handles various [ActionButtonType] commands including:
     * - Utility actions: Clearing the state ([ActionButtonType.ClearAll]), clearing only the current input ([ActionButtonType.ClearInput]),
     *   deleting the last character ([ActionButtonType.BackSpace]), or toggling the sign ([ActionButtonType.ToggleSign]).
     * - Evaluation: Triggers the calculation of the pending operation ([ActionButtonType.Evaluate]).
     * - Arithmetic operations: Sets the operator for the next calculation. If an operation is already pending,
     *   it evaluates the existing expression before applying the new operator.
     *
     * @param action The specific [ActionButtonType] to be performed.
     */
    fun onAction(action: ActionButtonType) {
        when (action) {
            ActionButtonType.ClearAll -> onClearAll()
            ActionButtonType.ClearInput -> onClearInput()
            ActionButtonType.BackSpace -> onBackSpace()
            ActionButtonType.Delete -> TODO()
            ActionButtonType.Evaluate -> {
                evaluate()
                _state.update { it.copy(isNewInputStarting = true) }
            }

            ActionButtonType.ToggleSign -> onToggleSign()
            else -> {
                if (_state.value.isNewInputStarting && _state.value.pendingOperation != ActionButtonType.None) {
                    _state.update { it.copy(pendingOperation = action) }
                    return
                }
                if (_state.value.pendingOperation != ActionButtonType.None)
                    evaluate()
                _state.update { currentState ->
                    currentState.copy(
                        pendingOperation = action,
                        pendingValue = currentState.input,
                        isNewInputStarting = true // Du müsstest dieses Feld im UICalculateState ergänzen
                    )
                }
            }
        }
    }

    private fun evaluate() {
        val currentState = _state.value
        val currentOp = currentState.pendingOperation

        // Wenn keine Operation ansteht, gibt es nichts zu rechnen
        if (currentOp == ActionButtonType.None || currentOp == ActionButtonType.Evaluate) return

        try {
            val result = when (currentOp) {
                ActionButtonType.Add -> currentState.pendingValue.add(currentState.input)
                ActionButtonType.Subtract -> currentState.pendingValue.subtract(currentState.input)
                ActionButtonType.Multiply -> currentState.pendingValue.multiply(currentState.input)
                ActionButtonType.Divide -> {
                    if (currentState.isNewInputStarting || currentState.input == BigDecimal.ZERO) {
                        // Fehlerbehandlung: Division durch Null
                        BigDecimal.ZERO
                    } else {
                        // Scale und RoundingMode verhindern Abstürze bei 1/3
                        currentState.pendingValue.divide(
                            currentState.input,
                            8,
                            java.math.RoundingMode.HALF_UP
                        )
                    }
                }

                else -> currentState.input
            }

            _state.update {
                it.copy(
                    inputString = result.stripTrailingZeros().toPlainString(),
                    pendingValue = BigDecimal.ZERO,
                    pendingOperation = ActionButtonType.None,
                    isNewInputStarting = true,
                )
            }
        } catch (e: Exception) {
            // Optional: Fehlerstatus setzen
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
                        inputString = currentState.pendingMemory,
                        pendingOperation = ActionButtonType.None,
                        pendingValue = BigDecimal.ZERO
                    )
                }

                workingInput.length == 2 && currentState.input < BigDecimal.ZERO -> {
                    currentState.copy(inputString = "0")
                }

                workingInput.length > 1 -> {
                    currentState.copy(inputString = currentState.inputString.dropLast(1))
                }

                else -> currentState.copy(inputString = "0")
            }
        }
    }

    private fun onToggleSign() {
        _state.update { it.copy(inputString = it.input.negate().toPlainString()) }
    }

    private fun onClearInput() {
        _state.update { it.copy(inputString = "0") }
    }

}