package com.gerwalex.calculator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

@Composable
fun rememberCalculatorSettings(
    block: CalculatorSettings.Builder.() -> Unit
): CalculatorSettings {
    return remember {
        calculatorSettings(block)
    }
}


data class CalculatorSettings(
    val initialValue: BigDecimal = BigDecimal.ZERO,
    val numberFormat: NumberFormat = NumberFormat.getNumberInstance(Locale.getDefault()),
    val isSignBtnShown: Boolean = true,
//    val minValue: BigDecimal = BigDecimal("-1e10"),
//    val maxValue: BigDecimal = BigDecimal("1e10"),
//    val isExpressionShown: Boolean = false,
//    val isExpressionEditable: Boolean = false,
//    val isAnswerBtnShown: Boolean = false,
//    val shouldEvaluateOnOperation: Boolean = true,
//    val orderOfOperationsApplied: Boolean = true,
) {
    class Builder {
        var initialValue: BigDecimal = BigDecimal.ZERO
        var numberFormat: NumberFormat = NumberFormat.getNumberInstance()
        var isSignBtnShown: Boolean = true
//        var minValue: BigDecimal = BigDecimal("-1e10")
//        var maxValue: BigDecimal = BigDecimal("1e10")
//        var isExpressionShown: Boolean = false
//        var isExpressionEditable: Boolean = false
//        var isAnswerBtnShown: Boolean = false
//        var shouldEvaluateOnOperation: Boolean = true
//        var orderOfOperationsApplied: Boolean = true

        fun build(): CalculatorSettings {
            return CalculatorSettings(
                initialValue, numberFormat, isSignBtnShown,
//                minValue, maxValue,
//                isExpressionShown, isExpressionEditable,
//                isAnswerBtnShown, shouldEvaluateOnOperation,
//                orderOfOperationsApplied
            )
        }
    }

}

// Die Einstiegsfunktion für die DSL
fun calculatorSettings(block: CalculatorSettings.Builder.() -> Unit): CalculatorSettings {
    return CalculatorSettings.Builder().apply(block).build()
}