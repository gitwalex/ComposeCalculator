package com.gerwalex.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gerwalex.calculator.arithmatic.CalculatorBrain
import com.gerwalex.calculator.arithmatic.UICalculateState
import com.gerwalex.calculator.common.ActionButton
import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.common.NumberButton
import com.gerwalex.calculator.common.NumberButtonType
import com.gerwalex.calculator.ui.theme.LocalColors
import java.math.BigDecimal

@Composable
fun CalculatorContent(
    modifier: Modifier,
    brain: CalculatorBrain,
    onResult: (BigDecimal) -> Unit = {}
) {
    val onResult by rememberUpdatedState(onResult)
    LaunchedEffect(brain) {
        snapshotFlow { brain.state }.collect { onResult(it.currentInput) }
    }
//    with(brain) {
//        ConstraintLayout(
//            modifier = modifier
//                .wrapContentSize()
//                .padding(start = 30.dp, end = 30.dp)
//        ) {
//            val (firstSpacer, secondSpacer, pendingMemory, inputText, buttonC, buttonOpenBracket, buttonClosedBracket, buttonX, buttonDivide, button7, button8, button9, button4, button5, button6, buttonMin) = createRefs()
//            val (thirdSpacer, button1, button2, button3, buttonSum, button0, buttonSquare, buttonDel, buttonEqual, buttonDot, fourthSpacer, fifthSpacer, sixSpacer, pendingOperations) = createRefs()
//
//            val guidelineTop = createGuidelineFromTop(0.08f)
//            createHorizontalChain(
//                buttonC,
//                buttonOpenBracket,
//                buttonClosedBracket,
//                buttonDivide,
//                chainStyle = ChainStyle.SpreadInside
//            )
//            createHorizontalChain(
//                button7,
//                button8,
//                button9,
//                buttonX,
//                chainStyle = ChainStyle.SpreadInside
//            )
//            createHorizontalChain(
//                button4,
//                button5,
//                button6,
//                buttonMin,
//                chainStyle = ChainStyle.SpreadInside
//            )
//
//            createHorizontalChain(
//                button1,
//                button2,
//                button3,
//                buttonSum,
//                chainStyle = ChainStyle.SpreadInside
//            )
//
//            createHorizontalChain(
//                button0,
//                buttonDot,
//                buttonSquare,
//                chainStyle = ChainStyle.SpreadInside
//            )
//
//            createHorizontalChain(
//                buttonDel,
//                buttonEqual,
//                chainStyle = ChainStyle.SpreadInside
//            )
//
//            if (state.pendingOperation != ActionButtonType.None) {
//                Text(
//                    modifier = Modifier
//                        .constrainAs(pendingOperations) {
//                            bottom.linkTo(inputText.top)
//                            end.linkTo(parent.end)
//                        },
//                    text = state.pendingOperation.type, textAlign = TextAlign.End,
//                    fontWeight = FontWeight.Black, fontSize = 20.sp
//                )
//                Text(
//                    modifier = Modifier
//                        .padding(end = 20.dp)
//                        .constrainAs(pendingMemory) {
//                            top.linkTo(pendingOperations.top)
//                            end.linkTo(inputText.end)
//                        },
//                    text = state.pendingMemory, textAlign = TextAlign.End,
//                    fontWeight = FontWeight.Black, fontSize = 20.sp
//                )
//            }
//            Text(
//                text = state.input,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .padding(end = 20.dp)
//                    .constrainAs(inputText) {
//                        top.linkTo(guidelineTop)
//                        end.linkTo(parent.end)
//                    }, textAlign = TextAlign.End, fontSize = 25.sp
//            )
//            Spacer(
//                modifier = Modifier
//                    .height(10.dp)
//                    .fillMaxWidth()
//                    .constrainAs(sixSpacer) {
//                        start.linkTo(parent.start)
//                        top.linkTo(inputText.bottom)
//                        end.linkTo(parent.end)
//                    })
//            ActionButton(
//                symbol = ActionButtonType.ClearAll,
//                modifier = Modifier
//                    .constrainAs(buttonC) {
//                        start.linkTo(parent.start)
//                        top.linkTo(sixSpacer.bottom)
//                    }
//            ) {
//                onAction(ActionButtonType.ClearAll)
//            }
//            ActionButton(
//                symbol = ActionButtonType.ClearInput,
//                modifier = Modifier
//                    .constrainAs(buttonOpenBracket) {
//                        top.linkTo(buttonC.top)
//                        bottom.linkTo(buttonC.bottom)
//                    }
//            ) {
//                onAction(ActionButtonType.ClearInput)
//            }
//            ActionButton(
//                symbol = ActionButtonType.ToggleSign,
//                modifier = Modifier
//                    .constrainAs(buttonClosedBracket) {
//                        top.linkTo(buttonC.top)
//                        bottom.linkTo(buttonC.bottom)
//                    }
//            ) {
//                onAction(ActionButtonType.ToggleSign)
//            }
//            ActionButton(
//                symbol = ActionButtonType.Divide,
//                modifier = Modifier
//                    .constrainAs(buttonDivide) {
//                        top.linkTo(buttonC.top)
//                        bottom.linkTo(buttonC.bottom)
//                        end.linkTo(parent.end)
//                    }) {
//                onAction(ActionButtonType.Divide)
//            }
//
//            Spacer(
//                modifier = Modifier
//                    .height(20.dp)
//                    .fillMaxWidth()
//                    .constrainAs(firstSpacer) {
//                        start.linkTo(parent.start)
//                        top.linkTo(buttonC.bottom)
//                        end.linkTo(parent.end)
//                    })
//
//            NumberButton(
//                symbol = NumberButtonType.Seven,
//                modifier = Modifier
//                    .constrainAs(button7) {
//                        start.linkTo(parent.start)
//                        top.linkTo(firstSpacer.bottom)
//                    }
//            ) {
//                onAction(NumberButtonType.Seven)
//            }
//            NumberButton(
//                symbol = NumberButtonType.Eight,
//                modifier = Modifier
//                    .constrainAs(button8) {
//                        top.linkTo(button7.top)
//                        bottom.linkTo(button7.bottom)
//                    }
//            ) {
//                onAction(NumberButtonType.Eight)
//            }
//            NumberButton(
//                symbol = NumberButtonType.Nine,
//                modifier = Modifier
//                    .constrainAs(button9) {
//                        bottom.linkTo(button7.bottom)
//                        top.linkTo(button7.top)
//                    }
//            ) {
//                onAction(NumberButtonType.Nine)
//            }
//            ActionButton(
//                symbol = ActionButtonType.Multiply,
//                modifier = Modifier
//                    .constrainAs(buttonX) {
//                        top.linkTo(button7.top)
//                        bottom.linkTo(button7.bottom)
//                        end.linkTo(firstSpacer.end)
//                    }
//
//            ) {
//                onAction(ActionButtonType.Multiply)
//            }
//            Spacer(
//                modifier = Modifier
//                    .height(20.dp)
//                    .fillMaxWidth()
//                    .constrainAs(secondSpacer) {
//                        start.linkTo(parent.start)
//                        top.linkTo(button7.bottom)
//                        end.linkTo(parent.end)
//                    })
//            NumberButton(
//                symbol = NumberButtonType.Four,
//                modifier = Modifier
//                    .constrainAs(button4) {
//                        start.linkTo(parent.start)
//                        top.linkTo(secondSpacer.bottom)
//                    }
//            ) {
//                onAction(NumberButtonType.Four)
//            }
//            NumberButton(
//                symbol = NumberButtonType.Five,
//                modifier = Modifier
//                    .constrainAs(button5) {
//                        top.linkTo(button4.top)
//                        bottom.linkTo(button4.bottom)
//                    }
//            ) {
//                onAction(NumberButtonType.Five)
//            }
//            NumberButton(
//                symbol = NumberButtonType.Six,
//                modifier = Modifier
//                    .constrainAs(button6) {
//                        top.linkTo(button4.top)
//                        bottom.linkTo(button4.bottom)
//                    }
//            ) {
//                onAction(NumberButtonType.Six)
//            }
//            ActionButton(
//                symbol = ActionButtonType.Subtract,
//                modifier = Modifier
//                    .constrainAs(buttonMin) {
//                        top.linkTo(button4.top)
//                        bottom.linkTo(button4.bottom)
//                        end.linkTo(parent.end)
//                    }
//            ) {
//                onAction(ActionButtonType.Subtract)
//            }
//            Spacer(
//                modifier = Modifier
//                    .height(20.dp)
//                    .fillMaxWidth()
//                    .constrainAs(thirdSpacer) {
//                        start.linkTo(parent.start)
//                        top.linkTo(button4.bottom)
//                        end.linkTo(parent.end)
//                    })
//            NumberButton(
//                symbol = NumberButtonType.One,
//                modifier = Modifier
//                    .constrainAs(button1) {
//                        start.linkTo(parent.start)
//                        top.linkTo(thirdSpacer.bottom)
//                    }
//            ) {
//                onAction(NumberButtonType.One)
//            }
//            NumberButton(
//                symbol = NumberButtonType.Two,
//                modifier = Modifier
//                    .constrainAs(button2) {
//                        top.linkTo(button1.top)
//                        bottom.linkTo(button1.bottom)
//                    }
//            ) {
//                onAction(NumberButtonType.Two)
//            }
//            NumberButton(
//                symbol = NumberButtonType.Three,
//                modifier = Modifier
//                    .constrainAs(button3) {
//                        top.linkTo(button1.top)
//                        bottom.linkTo(button1.bottom)
//                    }
//            ) {
//                onAction(NumberButtonType.Three)
//            }
//            ActionButton(
//                symbol = ActionButtonType.Add,
//                modifier = Modifier
//                    .constrainAs(buttonSum) {
//                        top.linkTo(button1.top)
//                        bottom.linkTo(button1.bottom)
//                        end.linkTo(parent.end)
//                    }
//            ) {
//                onAction(ActionButtonType.Add)
//            }
//            Spacer(
//                modifier = Modifier
//                    .height(20.dp)
//                    .fillMaxWidth()
//                    .constrainAs(fourthSpacer) {
//                        start.linkTo(parent.start)
//                        top.linkTo(button1.bottom)
//                        end.linkTo(parent.end)
//                    })
//            NumberButton(
//                symbol = NumberButtonType.Zero,
//                modifier = Modifier
//                    .width(140.dp)
//                    .constrainAs(button0) {
//                        start.linkTo(parent.start)
//                        top.linkTo(fourthSpacer.bottom)
//                    }
//            ) {
//                onAction(NumberButtonType.Zero)
//            }
//            NumberButton(
//                symbol = NumberButtonType.Period,
//                modifier = Modifier
//                    .constrainAs(buttonDot) {
//                        top.linkTo(button0.top)
//                        bottom.linkTo(button0.bottom)
//                    }
//            ) {
//                onAction(NumberButtonType.Period)
//            }
//            ActionButton(
//                symbol = ActionButtonType.BackSpace,
//                modifier = Modifier
//                    .constrainAs(buttonSquare) {
//                        top.linkTo(button0.top)
//                        bottom.linkTo(button0.bottom)
//                    }
//            ) {
//                onAction(ActionButtonType.BackSpace)
//            }
//            ActionButton(
//                symbol = ActionButtonType.Delete,
//                modifier = Modifier
//                    .constrainAs(buttonDel) {
//                        top.linkTo(fifthSpacer.bottom)
//                        start.linkTo(parent.start)
//                    }
//            ) {
//                onAction(ActionButtonType.Delete)
//            }
//            Spacer(
//                modifier = Modifier
//                    .height(20.dp)
//                    .fillMaxWidth()
//                    .constrainAs(fifthSpacer) {
//                        start.linkTo(parent.start)
//                        top.linkTo(button0.bottom)
//                        end.linkTo(parent.end)
//                    })
//            ActionButton(
//                symbol = ActionButtonType.Evaluate,
//                modifier = Modifier
//                    .width(210.dp)
//                    .constrainAs(buttonEqual) {
//                        end.linkTo(parent.end)
//                        top.linkTo(buttonDel.top)
//                        bottom.linkTo(buttonDel.bottom)
//                    }
//            ) {
//                onAction(ActionButtonType.Evaluate)
//            }
//        }
//    }
//}
//
//@Composable
//fun CalculartorCon(modifier: Modifier = Modifier) {
//    val brain = viewModel<CalculatorBrain>()
    CalculatorLayout(
        state = brain.state, onAction = {
            brain.onAction(it)
        },
        onNumber = {
            brain.onAction(it)
        })

}

@Composable
fun CalculatorLayout(
    state: UICalculateState,
    onAction: (ActionButtonType) -> Unit,
    onNumber: (NumberButtonType) -> Unit,

    ) {
    val haptic = LocalHapticFeedback.current
    val customColors = LocalColors.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Abstand zwischen den Zeilen
    ) {
        // --- DISPLAY BEREICH ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Nimmt restlichen Platz oben ein
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            if (state.pendingOperation != ActionButtonType.None) {
                Text(
                    text = "${state.pendingMemory} ${state.pendingOperation.type}",
                    color = customColors.fontColorPurple,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
            }
            Text(
                text = state.input,
                color = customColors.fontColorPurple,
                fontSize = 45.sp, // Schön groß im M3 Style
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End
            )
        }

        // --- BUTTONS BEREICH ---
        // Zeile 1: C, ( , ) , /
        CalculatorRow {
            ActionButton(
                ActionButtonType.ClearAll,
                Modifier.weight(1f)
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.LongPress) }
            ActionButton(
                ActionButtonType.ClearInput,
                Modifier.weight(1f)
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            ActionButton(
                ActionButtonType.ToggleSign,
                Modifier.weight(1f)
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            ActionButton(
                ActionButtonType.Divide,
                Modifier.weight(1f)
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
        }

        // Zeile 2: 7, 8, 9, X
        CalculatorRow {
            NumberButton(
                NumberButtonType.Seven,
                Modifier.weight(1f)
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            NumberButton(
                NumberButtonType.Eight,
                Modifier.weight(1f)
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            NumberButton(
                NumberButtonType.Nine,
                Modifier.weight(1f)
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            ActionButton(
                ActionButtonType.Multiply,
                Modifier.weight(1f)
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
        }
        CalculatorRow {
            NumberButton(
                NumberButtonType.Four,
                Modifier.weight(1f)
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            NumberButton(
                NumberButtonType.Five,
                Modifier.weight(1f)
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            NumberButton(
                NumberButtonType.Six,
                Modifier.weight(1f)
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            ActionButton(
                ActionButtonType.Subtract,
                Modifier.weight(1f)
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
        }
        CalculatorRow {
            NumberButton(
                NumberButtonType.One,
                Modifier.weight(1f)
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            NumberButton(
                NumberButtonType.Two,
                Modifier.weight(1f)
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            NumberButton(
                NumberButtonType.Three,
                Modifier.weight(1f)
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            ActionButton(
                ActionButtonType.Add,
                Modifier.weight(1f)
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
        }

        // ... Zeile 3 & 4 analog ...

        // Zeile 5: Spezielle Breiten (0, ., Backspace)
        CalculatorRow {
            NumberButton(
                NumberButtonType.Zero,
                Modifier.weight(1f)
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            NumberButton(
                NumberButtonType.Period,
                Modifier.weight(1f)
            ) { onNumber(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            ActionButton(
                ActionButtonType.ToggleSign,
                Modifier.weight(1f)
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
            ActionButton(
                ActionButtonType.BackSpace,
                Modifier.weight(1f)
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
        }

        // Letzte Zeile: Delete & Evaluate
        CalculatorRow {
            ActionButton(
                ActionButtonType.Delete,
                Modifier.weight(1.2f)
            ) { onAction(it); haptic.performHapticFeedback(HapticFeedbackType.LongPress) }
            ActionButton(
                ActionButtonType.Evaluate,
                Modifier.weight(2.0f)
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
    val brain = viewModel<CalculatorBrain>().also {
        it.state = UICalculateState(
            input = "123",
            pendingValue = BigDecimal(456),
            pendingOperation = ActionButtonType.Add
        )
    }
    Surface {
        CalculatorLayout(
            state = brain.state,
            onAction = {},
            onNumber = {}
        )
    }
}
