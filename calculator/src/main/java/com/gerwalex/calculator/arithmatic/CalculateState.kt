package com.gerwalex.calculator.arithmatic

import com.gerwalex.calculator.CalculatorSettings
import com.gerwalex.calculator.common.ActionButtonType
import java.math.BigDecimal

data class UICalculateState(
    val settings: CalculatorSettings = CalculatorSettings(),
    val inputString: String = settings.initialValue.toPlainString(),
    val pendingValue: BigDecimal = BigDecimal.ZERO,
    val pendingOperation: ActionButtonType = ActionButtonType.None,
    val isNewInputStarting: Boolean = false,
    val error: Int? = null,
) {
    val pendingMemory = pendingValue.toPlainString()
    val input: BigDecimal = inputString.toBigDecimalOrNull() ?: BigDecimal.ZERO
    val formattedResult = settings.numberFormat.format(input)

}