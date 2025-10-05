package com.gerwalex.calculator.arithmatic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.common.NumberButtonType
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorBrain() : ViewModel() {


    var state by mutableStateOf(UICalculateState())

    /**
     * Handles the action when a number button is pressed.
     *
     * It updates the current input in the calculator's state based on the pressed number button.
     * - If the period button is pressed and the input already contains a period, it does nothing.
     * - If the zero button is pressed and the input is already "0", it does nothing.
     * - If the input is "0", it replaces "0" with the pressed number.
     * - Otherwise, it appends the pressed number to the current input.
     *
     * @param action The [NumberButtonType] representing the pressed number button.
     */
    fun onAction(action: NumberButtonType) {
        if (state.pendingOperation == ActionButtonType.ClearInput) {
            state = state.copy(input = "0")

        }
        val input = when {
            action == NumberButtonType.Period && state.input.contains('.') -> return
            action == NumberButtonType.Zero && state.input == "0" -> return
            state.input == "0" -> action.type
            else -> state.input + action.type
        }
        state = state.copy(input = input)
    }


    /**
     * Handles an action button press.
     *
     * This function first executes any pending operation. Then, based on the `action` provided,
     * it performs a specific calculator action:
     * - **ClearAll**: Resets the calculator to its initial state.
     * - **ClearInput**: Clears the current input field.
     * - **BackSpace**: Removes the last character from the input field.
     * - **Delete**: (Not yet implemented - marked as TODO)
     * - **Evaluate**: Executes the pending operation.
     * - **ToggleSign**: Changes the sign of the current input or the pending value.
     * - **Other Actions (Add, Subtract, Multiply, Divide)**: Sets the current action as the pending
     *   operation, stores the current input as the pending value, and resets the input field to "0".
     *
     * @param action The [ActionButtonType] representing the action to be performed.
     */
    fun onAction(action: ActionButtonType) {
        when (action) {
            ActionButtonType.ClearAll -> onClearAll()
            ActionButtonType.ClearInput -> onClearInput()
            ActionButtonType.BackSpace -> onBackSpace()
            ActionButtonType.Delete -> TODO()
            ActionButtonType.Evaluate -> {
                evaluate()
                state = state.copy(pendingOperation = ActionButtonType.ClearInput)
            }

            ActionButtonType.ToggleSign -> onToggleSign()
            else -> {
                evaluate()
                state = state.copy(
                    pendingOperation = action,
                    pendingValue = state.currentInput,
                    input = "0",
                )
            }
        }
    }

    /**
     * Executes the pending arithmetic operation based on the current calculator state.
     *
     * This function checks the `pendingOperation` in the `state` and calls the
     * corresponding arithmetic function (e.g., `onAdd()`, `onSubtract()`).
     * If the `pendingOperation` is `ActionButtonType.None`, it does nothing.
     * If an invalid operation is encountered, it throws an `IllegalStateException`.
     */
    private fun evaluate() {
        when (state.pendingOperation) {
            ActionButtonType.None -> state.copy(pendingValue = BigDecimal.ZERO)
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

    /**
     * Handles the backspace action.
     *
     * This function modifies the calculator's input based on its current state:
     * - If the input is "0" and there is a pending operation, it restores the `pendingMemory`
     *   to the input, clears the `pendingOperation`, and resets `pendingValue` to zero.
     *   This allows the user to correct the second operand of an operation.
     * - If the input has a length of 2 and the `currentInput` is negative (e.g., "-5"),
     *   it resets the input to "0".
     * - If the input has more than one character, it removes the last character.
     * - Otherwise (e.g., input has one character and no pending operation, or input is "0" with no pending operation),
     *   it sets the input to "0".
     */
    private fun onBackSpace() {
        state = when {
            state.input == "0" && state.pendingOperation != ActionButtonType.None -> {
                state.copy(
                    input = state.pendingMemory,
                    pendingOperation = ActionButtonType.None,
                    pendingValue = BigDecimal.ZERO
                )
            }

            state.input.length == 2 && state.currentInput < BigDecimal.ZERO -> {
                state.copy(input = "0")
            }

            state.input.length > 1 -> {
                state.copy(input = state.input.dropLast(1))
            }

            else -> state.copy(input = "0")
        }
    }

    /**
     * Toggles the sign of the current input.
     *
     * This function negates the `currentInput` in the calculator's state and updates
     * the `input` field with the string representation of the negated value.
     */
    private fun onToggleSign() {
        state = state.copy(input = state.currentInput.negate().toString())
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
