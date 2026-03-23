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
                { onNumberAction(NumberButtonType.Seven) }
                NumberButton(symbol = NumberButtonType.Eight)
                { onNumberAction(NumberButtonType.Eight) }
                NumberButton(symbol = NumberButtonType.Nine)
                { onNumberAction(NumberButtonType.Nine) }
            }
            Row(
                horizontalArrangement = spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NumberButton(symbol = NumberButtonType.Four)
                { onNumberAction(NumberButtonType.Four) }
                NumberButton(symbol = NumberButtonType.Five)
                { onNumberAction(NumberButtonType.Five) }
                NumberButton(symbol = NumberButtonType.Six)
                { onNumberAction(NumberButtonType.Six) }
            }
            Row(
                horizontalArrangement = spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NumberButton(symbol = NumberButtonType.One)
                { onNumberAction(NumberButtonType.One) }
                NumberButton(symbol = NumberButtonType.Two)
                { onNumberAction(NumberButtonType.Two) }
                NumberButton(symbol = NumberButtonType.Three)
                { onNumberAction(NumberButtonType.Three) }
            }
            Row(
                horizontalArrangement = spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NumberButton(symbol = NumberButtonType.Zero)
                { onNumberAction(NumberButtonType.Zero) }
                NumberButton(symbol = NumberButtonType.Period)
                { onNumberAction(NumberButtonType.Period) }
                NumberButton(symbol = NumberButtonType.BackSpace)
                { onNumberAction(NumberButtonType.BackSpace) }
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