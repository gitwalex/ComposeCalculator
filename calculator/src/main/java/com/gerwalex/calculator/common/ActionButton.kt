package com.gerwalex.calculator.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gerwalex.calculator.CalculatorLayout
import com.gerwalex.calculator.arithmatic.UICalculateState
import com.gerwalex.calculator.rememberCalculatorSettings
import com.gerwalex.calculator.ui.theme.CalculatorStyle
import com.gerwalex.calculator.ui.theme.CalculatorStyleDefaults
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

enum class ActionButtonType(val type: String) {
    Add("+"),
    Subtract("-"),
    Divide("/"),
    Multiply("*"),
    ClearAll("C"),
    ClearInput("CE"),
    BackSpace("⌫"),
    ToggleSign("+/-"),
    Evaluate("="),
    Delete("DEL"),
    None(" "),
}

@Composable
fun ActionButton(
    symbol: ActionButtonType,
    modifier: Modifier = Modifier,
    colors: CalculatorStyle,
    enabled: Boolean = true,
    onAction: (ActionButtonType) -> Unit
) {
    val onClick by rememberUpdatedState(onAction)
    TextButton(
        onClick = { onClick(symbol) },
        enabled = enabled,
        modifier = modifier
            .width(70.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(color = colors.actionButtonColor)
            .testTag(symbol.type)

    ) {
        Text(
            text = symbol.type,
            style = colors.actionButtonTextStyle,
            maxLines = 1,
        )
    }
}

@Preview
@Composable
private fun CalculatorActionButtonDialog() {
    val settings = rememberCalculatorSettings {
        numberFormat = NumberFormat.getNumberInstance(Locale.GERMAN)
        initialValue = BigDecimal(12345)
    }
    val state = UICalculateState(
        pendingValue = BigDecimal(456),
        pendingOperation = ActionButtonType.Add,
        settings = settings,
    )

    Surface {
        CalculatorLayout(
            state = state,
            colors = CalculatorStyleDefaults.defaultColors(),
            onAction = {},
            onNumber = {}
        )
    }
}

@Preview
@Composable
private fun ActionButtonAdd() {
    MaterialTheme {
        ActionButton(
            symbol = ActionButtonType.Add,
            colors = CalculatorStyleDefaults.defaultColors()
        ) {}
    }
}

@Preview
@Composable
private fun ActionButtonMultiply() {
    MaterialTheme {
        ActionButton(
            symbol = ActionButtonType.Multiply,
            colors = CalculatorStyleDefaults.defaultColors()
        ) {}
    }
}

@Preview
@Composable
private fun ActionButtonToggle() {
    MaterialTheme {
        ActionButton(
            symbol = ActionButtonType.ToggleSign,
            colors = CalculatorStyleDefaults.defaultColors()
        ) {}
    }
}

