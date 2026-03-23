package com.gerwalex.calculator.common

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gerwalex.calculator.CalculatorLayout
import com.gerwalex.calculator.arithmatic.UICalculateState
import java.math.BigDecimal

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
    BackSpace("⌫"),


}

@Composable
fun NumberButton(
    symbol: NumberButtonType,
    modifier: Modifier = Modifier,
    colorFont: Color = MaterialTheme.colorScheme.onPrimary,
    onNumber: (NumberButtonType) -> Unit,
) {
    val haptics = LocalHapticFeedback.current
    Button(
        onClick = {
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
            // Oder HapticFeedbackType.TextHandleMove für ein kürzeres Tippen

            onNumber(symbol)
        },
        modifier = modifier
            .padding(4.dp)
            .aspectRatio(1f), // Sorgt für quadratische Form
        shape = CircleShape, // Oder RoundedCornerShape(16.dp) für M3 Look
//        colors = ButtonDefaults.buttonColors(
//            containerColor = LocalColors.current.numberButtonColor
//        )
    ) {
        Text(
            text = symbol.type,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            color = colorFont
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun CalculatorNumberButtonDialog() {
    val state = UICalculateState(
        input = "123".toBigDecimal(),
        pendingValue = BigDecimal(456),
        pendingOperation = ActionButtonType.Add
    )

    Surface {
        CalculatorLayout(
            state = state,
            onAction = {},
            onNumber = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    NumberButton(symbol = NumberButtonType.One) {}
}