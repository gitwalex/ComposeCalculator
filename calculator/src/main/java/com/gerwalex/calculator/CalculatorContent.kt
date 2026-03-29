package com.gerwalex.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gerwalex.calculator.arithmatic.UICalculateState
import com.gerwalex.calculator.common.ActionButton
import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.common.NumberButton
import com.gerwalex.calculator.common.NumberButtonType
import com.gerwalex.calculator.ui.theme.CalculatorStyle


@Composable
fun CalculatorLayout(
    state: UICalculateState,
    colors: CalculatorStyle,
    modifier: Modifier = Modifier,
    onAction: (ActionButtonType) -> Unit,
    onNumber: (NumberButtonType) -> Unit,

    ) {
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = modifier
            .padding(horizontal = 30.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Abstand zwischen den Zeilen
    ) {
        // --- DISPLAY BEREICH ---
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text =
                    if (state.pendingOperation == ActionButtonType.None)
                        ""
                    else
                        "${state.pendingMemory} ${state.pendingOperation.type}",
                style = colors.displayTextStyle.copy(fontSize = 24.sp),
                textAlign = TextAlign.End,
                modifier = Modifier.testTag("PendingAction")
            )

            Text(
                text = state.formattedResult,
                style = colors.displayTextStyle,
                textAlign = TextAlign.End,
                modifier = Modifier.testTag("Input")
            )
        }
        // --- BUTTONS BEREICH ---
        // Zeile 1: C, ( , ) , /
        CalculatorRow {
            ActionButton(
                ActionButtonType.ClearAll,
                Modifier.weight(1f),
                colors = colors,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
            ActionButton(
                ActionButtonType.ClearInput,
                Modifier.weight(1f),
                colors = colors,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
            ActionButton(
                ActionButtonType.ToggleSign,
                Modifier.weight(1f),
                colors = colors,
                enabled = state.settings.isSignBtnShown
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
            ActionButton(
                ActionButtonType.BackSpace,
                Modifier.weight(1f),
                colors = colors,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
        }

        // Zeile 2: 7, 8, 9, X
        CalculatorRow {
            NumberButton(
                NumberButtonType.Seven,
                colors = colors,
                Modifier.weight(1f),
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
            NumberButton(
                NumberButtonType.Eight,
                colors = colors,
                Modifier.weight(1f),
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
            NumberButton(
                NumberButtonType.Nine,
                colors = colors,
                Modifier.weight(1f),
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
            ActionButton(
                ActionButtonType.Divide,
                Modifier.weight(1f),
                colors = colors,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
        }
        CalculatorRow {
            NumberButton(
                NumberButtonType.Four,
                colors = colors,
                Modifier.weight(1f),
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
            NumberButton(
                NumberButtonType.Five,
                colors = colors,
                Modifier.weight(1f),
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
            NumberButton(
                NumberButtonType.Six,
                colors = colors,
                Modifier.weight(1f),
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
            ActionButton(
                ActionButtonType.Multiply,
                Modifier.weight(1f),
                colors = colors,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
        }
        CalculatorRow {
            NumberButton(
                NumberButtonType.One,
                colors = colors,
                Modifier.weight(1f),
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
            NumberButton(
                NumberButtonType.Two,
                colors = colors,
                Modifier.weight(1f),
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
            NumberButton(
                NumberButtonType.Three,
                colors = colors,
                Modifier.weight(1f),
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
            ActionButton(
                ActionButtonType.Subtract,
                Modifier.weight(1f),
                colors = colors,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
        }

        CalculatorRow {
            NumberButton(
                NumberButtonType.Zero,
                enabled = state.inputString != "0",
                colors = colors,
                modifier = Modifier.weight(1f),
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
            NumberButton(
                NumberButtonType.Period,
                colors = colors,
                Modifier.weight(1f),
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
            ActionButton(
                ActionButtonType.Evaluate,
                Modifier.weight(1f),
                colors = colors,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.LongPress) }
            ActionButton(
                ActionButtonType.Add,
                Modifier.weight(1f),
                colors = colors,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.Confirm) }
        }

    }
}

// Hilfs-Composable für weniger Redundanz
@Composable
fun CalculatorRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp), // Abstand zwischen Buttons
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

@Preview
@Composable
fun CalculatorContentPreview() {
    CalculatorScreenPreview()
}
