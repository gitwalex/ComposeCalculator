package com.gerwalex.calculator.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class NumberButtonType(val type: String) {
    One("1"),
    Two("2"),
    Three("3"),
    Four("4"),
    Five("5"),
    Six("6"),
    Seven("7"),
    Eight("8"),
    Nine("9"),
    Zero("0"),
    Period("."),

}

@Composable
fun NumberButton(
    symbol: NumberButtonType,
    modifier: Modifier = Modifier,
    colorBackground: Color = MaterialTheme.colorScheme.background,
    colorFont: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit
) {
    val onClick by rememberUpdatedState(onClick)
    val haptics = LocalHapticFeedback.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(70.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = colorBackground)
            .clickable {
                haptics.performHapticFeedback(HapticFeedbackType.Confirm)
                onClick()
            }
            .padding(start = 6.dp, end = 6.dp, top = 9.dp, bottom = 9.dp)
    ) {
        Text(
            text = symbol.type,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            color = colorFont
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    NumberButton(symbol = NumberButtonType.One) {}
}