package com.gerwalex.calculator.common

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gerwalex.calculator.CalculatorLayout
import com.gerwalex.calculator.arithmatic.UICalculateState
import com.gerwalex.calculator.ui.theme.CalculatorStyle
import com.gerwalex.calculator.ui.theme.CalculatorStyleDefaults
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
    colors: CalculatorStyle,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onNumber: (NumberButtonType) -> Unit,
) {
    TextButton(
        onClick = { onNumber(symbol) },
        enabled = enabled,
        modifier = modifier
            .width(70.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(color = colors.numberButtonColor)
            .testTag(symbol.type)

    ) {
        Text(
            text = symbol.type,
            style = colors.numberButtonTextStyle,
            maxLines = 1,
        )
    }

}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun CalculatorNumberButtonDialog() {
    val state = UICalculateState(
        inputString = "123",
        pendingValue = BigDecimal(456),
        pendingOperation = ActionButtonType.Add
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

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    NumberButton(symbol = NumberButtonType.One, CalculatorStyleDefaults.defaultColors()) {}
}