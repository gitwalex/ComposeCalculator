package com.gerwalex.calculator.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    colorBackground: Color = MaterialTheme.colorScheme.primary,
    colorFont: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit
) {
    val onClick by rememberUpdatedState(onClick)
    val haptics = LocalHapticFeedback.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(70.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(color = colorBackground)
            .clickable {
                haptics.performHapticFeedback(HapticFeedbackType.Confirm)
                onClick()
            }
            .padding(start = 6.dp, end = 6.dp, top = 9.dp, bottom = 9.dp)
    ) {
        BasicText(
            modifier = Modifier.align(Alignment.Center),
            text = symbol.type,
            maxLines = 1,
            color = ColorProducer { colorFont },
            autoSize = TextAutoSize.StepBased(12.sp, maxFontSize = 25.sp)
        )
    }
}

@Preview
@Composable
private fun ActionButtonAdd() {
    MaterialTheme {
        ActionButton(symbol = ActionButtonType.Add) {}
    }
}

@Preview
@Composable
private fun ActionButtonMultiply() {
    MaterialTheme {
        ActionButton(symbol = ActionButtonType.Multiply) {}
    }
}

@Preview
@Composable
private fun ActionButtonToggle() {
    MaterialTheme {
        ActionButton(symbol = ActionButtonType.ToggleSign) {}
    }
}

