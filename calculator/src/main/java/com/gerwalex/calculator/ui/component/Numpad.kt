package com.gerwalex.calculator.ui.component

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gerwalex.calculator.arithmatic.CalculatorBrain
import com.gerwalex.calculator.common.NumberButton
import com.gerwalex.calculator.common.NumberButtonType

@Composable
fun Numpad(
    modifier: Modifier = Modifier, brain: CalculatorBrain,
) {
    with(brain) {
        Column(
            modifier = modifier,
            verticalArrangement = spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NumberButton(symbol = NumberButtonType.Seven)
                { onAction(NumberButtonType.Seven) }
                NumberButton(symbol = NumberButtonType.Eight)
                { onAction(NumberButtonType.Eight) }
                NumberButton(symbol = NumberButtonType.Nine)
                { onAction(NumberButtonType.Nine) }
            }
            Row(
                horizontalArrangement = spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NumberButton(symbol = NumberButtonType.Four)
                { onAction(NumberButtonType.Four) }
                NumberButton(symbol = NumberButtonType.Five)
                { onAction(NumberButtonType.Five) }
                NumberButton(symbol = NumberButtonType.Six)
                { onAction(NumberButtonType.Six) }
            }
            Row(
                horizontalArrangement = spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NumberButton(symbol = NumberButtonType.One)
                { onAction(NumberButtonType.One) }
                NumberButton(symbol = NumberButtonType.Two)
                { onAction(NumberButtonType.Two) }
                NumberButton(symbol = NumberButtonType.Three)
                { onAction(NumberButtonType.Three) }
            }
            Row(
                horizontalArrangement = spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NumberButton(symbol = NumberButtonType.Zero)
                { onAction(NumberButtonType.Zero) }
                NumberButton(symbol = NumberButtonType.Period)
                { onAction(NumberButtonType.Period) }
                NumberButton(symbol = NumberButtonType.BackSpace)
                { onAction(NumberButtonType.BackSpace) }
            }
        }
    }

}

@Preview
@Composable
private fun NumpadPreview() {
    val brain = viewModel { CalculatorBrain() }
    Numpad(brain = brain)
}