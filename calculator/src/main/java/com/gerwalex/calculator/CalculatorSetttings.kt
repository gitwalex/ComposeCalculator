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


class CalculatorSettings(
    val initialValue: BigDecimal = BigDecimal.ZERO,
    val numberFormat: NumberFormat = NumberFormat.getNumberInstance(),
    val minValue: BigDecimal = BigDecimal("-1e10"),
    val maxValue: BigDecimal = BigDecimal("1e10"),
    val expressionShown: Boolean = false,
    val expressionEditable: Boolean = false,
    val zeroShownWhenNoValue: Boolean = true,
    val answerBtnShown: Boolean = false,
    val signBtnShown: Boolean = true,
    val shouldEvaluateOnOperation: Boolean = true,
    val orderOfOperationsApplied: Boolean = true,
    val locale: Locale = Locale.getDefault(),
//    val numpadLayout = CalcNumpadLayout.CALCULATOR, {
) {
    class Builder {
        var initialValue: BigDecimal = BigDecimal.ZERO
        var numberFormat: NumberFormat = NumberFormat.getNumberInstance()
        var minValue: BigDecimal = BigDecimal("-1e10")
        var maxValue: BigDecimal = BigDecimal("1e10")
        var expressionShown: Boolean = false
        var expressionEditable: Boolean = false
        var zeroShownWhenNoValue: Boolean = true
        var answerBtnShown: Boolean = false
        var signBtnShown: Boolean = true
        var shouldEvaluateOnOperation: Boolean = true
        var orderOfOperationsApplied: Boolean = true
        val locale: Locale = Locale.getDefault()

        fun build(): CalculatorSettings {
            return CalculatorSettings(
                initialValue, numberFormat, minValue, maxValue,
                expressionShown, expressionEditable, zeroShownWhenNoValue,
                answerBtnShown, signBtnShown, shouldEvaluateOnOperation,
                orderOfOperationsApplied, locale
            )
        }
    }

}

// Die Einstiegsfunktion für die DSL
fun calculatorSettings(block: CalculatorSettings.Builder.() -> Unit): CalculatorSettings {
    return CalculatorSettings.Builder().apply(block).build()
}