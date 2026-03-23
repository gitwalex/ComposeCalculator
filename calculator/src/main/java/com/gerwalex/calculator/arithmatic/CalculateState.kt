package com.gerwalex.calculator.arithmatic

import com.gerwalex.calculator.common.ActionButtonType
import java.math.BigDecimal

data class UICalculateState(
    val input: BigDecimal = BigDecimal.ZERO,
    val pendingValue: BigDecimal = BigDecimal.ZERO,
    val pendingOperation: ActionButtonType = ActionButtonType.None,
    val error: Int? = null,
) {
    val pendingMemory = pendingValue.toPlainString()
    val inputString: String = input.toPlainString()

}