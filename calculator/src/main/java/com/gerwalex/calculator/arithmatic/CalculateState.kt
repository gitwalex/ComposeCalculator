package com.gerwalex.calculator.arithmatic

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import com.gerwalex.calculator.common.ActionButtonType
import java.math.BigDecimal

data class UICalculateState(
    val input: String = "0",
    val pendingValue: BigDecimal = BigDecimal.ZERO,
    val pendingOperation: ActionButtonType = ActionButtonType.None,
    var toggleSign: Boolean = false,
    val error: Int? = null,
) {
    val pendingMemory by derivedStateOf { pendingValue.toString() }
    val currentInput: BigDecimal by derivedStateOf {
        if (toggleSign)
            input.toBigDecimal().negate() else input.toBigDecimal()
    }
}