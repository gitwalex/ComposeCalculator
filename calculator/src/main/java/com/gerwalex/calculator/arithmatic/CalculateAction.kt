package com.gerwalex.calculator.arithmatic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
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
    Ignore("Ignore"),
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    symbol: ActionButtonType,
    colorBackground: Color = MaterialTheme.colorScheme.background,
    colorFont: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit
) {
    val onClick by rememberUpdatedState(onClick)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(color = colorBackground)
            .clickable { onClick() }
            .padding(start = 6.dp, end = 6.dp, top = 9.dp, bottom = 9.dp)
            .then(modifier)
    ) {
        Text(
            text = symbol.type,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            color = colorFont
        )
    }
}

