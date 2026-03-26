package com.gerwalex.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gerwalex.calculator.arithmatic.UICalculateState
import com.gerwalex.calculator.common.ActionButton
import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.common.NumberButton
import com.gerwalex.calculator.common.NumberButtonType
import java.math.BigDecimal


@Composable
fun CalculatorLayout(
    state: UICalculateState,
    modifier: Modifier = Modifier,
    buttonColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
    colorFont: Color = MaterialTheme.colorScheme.surface,
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
                    if (state.pendingOperation != ActionButtonType.None)
                        "${state.pendingMemory} ${state.pendingOperation.type}"
                    else
                        "",
                color = buttonColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.testTag("PendingAction")
            )

            Text(
                text = state.formattedResult,
                color = buttonColor,
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
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
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.LongPress) }
            ActionButton(
                ActionButtonType.ClearInput,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            ActionButton(
                ActionButtonType.ToggleSign,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            ActionButton(
                ActionButtonType.Divide,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
        }

        // Zeile 2: 7, 8, 9, X
        CalculatorRow {
            NumberButton(
                NumberButtonType.Seven,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            NumberButton(
                NumberButtonType.Eight,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            NumberButton(
                NumberButtonType.Nine,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            ActionButton(
                ActionButtonType.Multiply,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
        }
        CalculatorRow {
            NumberButton(
                NumberButtonType.Four,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            NumberButton(
                NumberButtonType.Five,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            NumberButton(
                NumberButtonType.Six,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            ActionButton(
                ActionButtonType.Subtract,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
        }
        CalculatorRow {
            NumberButton(
                NumberButtonType.One,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            NumberButton(
                NumberButtonType.Two,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            NumberButton(
                NumberButtonType.Three,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            ActionButton(
                ActionButtonType.Add,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
        }

        CalculatorRow {
            NumberButton(
                NumberButtonType.Zero,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            NumberButton(
                NumberButtonType.Period,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            ActionButton(
                ActionButtonType.ToggleSign,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            ActionButton(
                ActionButtonType.BackSpace,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
        }

        CalculatorRow {
            ActionButton(
                ActionButtonType.Delete,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.LongPress) }
            ActionButton(
                ActionButtonType.Evaluate,
                Modifier.weight(1f),
                buttonColor = buttonColor,
                colorFont = colorFont,
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.LongPress) }
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
private fun CalculaterContent() {
    val state = UICalculateState(
        inputString = "123",
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
