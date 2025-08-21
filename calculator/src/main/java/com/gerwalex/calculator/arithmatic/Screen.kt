package com.gerwalex.calculator.arithmatic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.common.NumberButtonType
import java.math.BigDecimal

@Composable
fun Screen(
    modifier: Modifier,
    state: UICalculateState,
    onAction: (ActionButtonType) -> Unit = {},
    onNumberClick: (NumberButtonType) -> Unit = {},
) {
    val contentWidthCommon = 70.dp
    Card(
        modifier = modifier
            .background(Color.DarkGray)
            .fillMaxWidth()
//            .padding(start = 30.dp, end = 30.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = state.input,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(end = 20.dp),
            )
            Text(
                modifier = modifier
                    .wrapContentSize()
                    .padding(end = 20.dp),
                text = state.pendingOperation.type, textAlign = TextAlign.End,
                fontWeight = FontWeight.Black, fontSize = 20.sp
            )
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    Screen(
        modifier = Modifier.fillMaxWidth(),
        state = UICalculateState(
            input = "123",
            pendingValue = BigDecimal(456),
            pendingOperation = ActionButtonType.Add
        )
    )


}
