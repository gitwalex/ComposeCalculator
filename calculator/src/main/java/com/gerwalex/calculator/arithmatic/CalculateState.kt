package com.gerwalex.calculator.arithmatic

import com.gerwalex.calculator.common.ActionButtonType
import java.math.BigDecimal

data class UICalculateState(
    val inputString: String = "0",
    val pendingValue: BigDecimal = BigDecimal.ZERO,
    val pendingOperation: ActionButtonType = ActionButtonType.None,
    val error: Int? = null,
) {
    val pendingMemory = pendingValue.toPlainString()
    val input: BigDecimal
        get() = inputString.toBigDecimalOrNull() ?: BigDecimal.ZERO
}