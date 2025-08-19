package com.gerwalex.calculator.arithmatic

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import com.gerwalex.calculator.common.ActionButtonType
import java.math.BigDecimal

data class UICalculateState(
    val input: String = "0",
    val currentValue: BigDecimal = BigDecimal.ZERO,
    val pendingOperation: ActionButtonType = ActionButtonType.Ignore,
    var toggleSign: Boolean = false,
    val error: Int? = null,
) {
    val result by derivedStateOf { currentValue.toString() }
    val currentInput: BigDecimal by derivedStateOf {
        if (input.isNotEmpty()) {
            if (toggleSign)
                input.toBigDecimal().negate() else input.toBigDecimal()
        } else BigDecimal.ZERO
    }
}