package com.gerwalex.calculator.arithmatic


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.gerwalex.calculator.common.ActionButton
import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.common.NumberButton
import com.gerwalex.calculator.common.NumberButtonType
import com.gerwalex.calculator.ui.component.myColors
import com.gerwalex.calculator.ui.theme.lightFontYellow
import java.math.BigDecimal

@Composable
fun CalculatorScreen(
    modifier: Modifier,
    calculateViewModel: CalculateViewModel,

    ) {
    CalculatorScreen(
        modifier = modifier,
        state = calculateViewModel.state,
        onAction = calculateViewModel::onAction,
        onNumberClick = calculateViewModel::onAction
    )
}

@Composable
fun CalculatorScreen(
    modifier: Modifier,
    state: UICalculateState,
    onAction: (ActionButtonType) -> Unit = {},
    onNumberClick: (NumberButtonType) -> Unit = {},
) {
    val contentWidthCommon = 70.dp
    ConstraintLayout(
        modifier = modifier
            .background(Color.LightGray)
//            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp)
    ) {
        val (firstSpacer, secondSpacer, inputText, outputText, buttonC, buttonOpenBracket, buttonClosedBracket, buttonX, buttonDivide, button7, button8, button9, button4, button5, button6, buttonMin) = createRefs()
        val (thirdSpacer, button1, button2, button3, buttonSum, button0, buttonSquare, buttonDel, buttonEqual, buttonDot, fourthSpacer, fifthSpacer, sixSpacer, pendingOperations) = createRefs()

        val guidelineTop = createGuidelineFromTop(0.08f)
        createHorizontalChain(
            buttonC,
            buttonOpenBracket,
            buttonClosedBracket,
            buttonDivide,
            chainStyle = ChainStyle.SpreadInside
        )
        createHorizontalChain(
            button7,
            button8,
            button9,
            buttonX,
            chainStyle = ChainStyle.SpreadInside
        )
        createHorizontalChain(
            button4,
            button5,
            button6,
            buttonMin,
            chainStyle = ChainStyle.SpreadInside
        )

        createHorizontalChain(
            button1,
            button2,
            button3,
            buttonSum,
            chainStyle = ChainStyle.SpreadInside
        )

        createHorizontalChain(
            button0,
            buttonDot,
            buttonSquare,
            chainStyle = ChainStyle.SpreadInside
        )

        createHorizontalChain(
            buttonDel,
            buttonEqual,
            chainStyle = ChainStyle.SpreadInside
        )

        Text(
            modifier = modifier
                .padding(end = 20.dp)
                .constrainAs(inputText) {
                    top.linkTo(guidelineTop)
                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
                },
            text = state.pendingMemory, textAlign = TextAlign.End,
            fontWeight = FontWeight.Black, fontSize = 20.sp
        )
        Text(
            modifier = modifier
//                .padding(end = 20.dp)
                .wrapContentSize()
                .constrainAs(pendingOperations) {
//                    top.linkTo(guidelineTop)

                    end.linkTo(parent.end)
                },
            text = state.pendingOperation.type, textAlign = TextAlign.End,
            fontWeight = FontWeight.Black, fontSize = 20.sp
        )

        Text(
            text = state.input,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(end = 20.dp)
                .fillMaxWidth()
                .padding(top = 16.dp)
                .constrainAs(outputText) {
                    start.linkTo(parent.start)
                    top.linkTo(inputText.bottom)
                    end.linkTo(parent.end)
                }, textAlign = TextAlign.End, fontSize = 25.sp
        )
        Spacer(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .constrainAs(sixSpacer) {
                    start.linkTo(parent.start)
                    top.linkTo(outputText.bottom)
                    end.linkTo(parent.end)
                })
        ActionButton(
            symbol = ActionButtonType.ClearAll,
            colorBackground = MaterialTheme.myColors.clearButtonColor,
            colorFont = Color.White,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(buttonC) {
                    start.linkTo(parent.start)
                    top.linkTo(sixSpacer.bottom)
                }
        ) {
            onAction(ActionButtonType.ClearAll)
        }
        ActionButton(
            symbol = ActionButtonType.ClearInput,
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = lightFontYellow,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(buttonOpenBracket) {
                    top.linkTo(buttonC.top)
                    bottom.linkTo(buttonC.bottom)
                }
        ) {
            onAction(ActionButtonType.ClearInput)
        }
        ActionButton(
            symbol = ActionButtonType.ToggleSign,
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = lightFontYellow,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(buttonClosedBracket) {
                    top.linkTo(buttonC.top)
                    bottom.linkTo(buttonC.bottom)
                }
        ) {
            onAction(ActionButtonType.ToggleSign)
        }
        ActionButton(
            symbol = ActionButtonType.Divide,
            colorBackground = MaterialTheme.myColors.operationButtonColor,
            colorFont = MaterialTheme.myColors.fontColorPurple,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(buttonDivide) {
                    top.linkTo(buttonC.top)
                    bottom.linkTo(buttonC.bottom)
                    end.linkTo(parent.end)
                }) {
            onAction(ActionButtonType.Divide)
        }

        Spacer(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .constrainAs(firstSpacer) {
                    start.linkTo(parent.start)
                    top.linkTo(buttonC.bottom)
                    end.linkTo(parent.end)
                })

        NumberButton(
            symbol = NumberButtonType.Seven,
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(button7) {
                    start.linkTo(parent.start)
                    top.linkTo(firstSpacer.bottom)
                }
        ) {
            onNumberClick(NumberButtonType.Seven)
        }
        NumberButton(
            symbol = NumberButtonType.Eight,
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(button8) {
                    top.linkTo(button7.top)
                    bottom.linkTo(button7.bottom)
                }
        ) {
            onNumberClick(NumberButtonType.Eight)
        }
        NumberButton(
            symbol = NumberButtonType.Nine,
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(button9) {
                    bottom.linkTo(button7.bottom)
                    top.linkTo(button7.top)
                }
        ) {
            onNumberClick(NumberButtonType.Nine)
        }
        ActionButton(
            symbol = ActionButtonType.Multiply,
            colorBackground = MaterialTheme.myColors.operationButtonColor,
            colorFont = MaterialTheme.myColors.fontColorPurple,
            modifier = Modifier
                .constrainAs(buttonX) {
                    top.linkTo(button7.top)
                    bottom.linkTo(button7.bottom)
                    end.linkTo(firstSpacer.end)
                }
                .width(70.dp)
        ) {
            onAction(ActionButtonType.Multiply)
        }
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .constrainAs(secondSpacer) {
                    start.linkTo(parent.start)
                    top.linkTo(button7.bottom)
                    end.linkTo(parent.end)
                })
        NumberButton(
            symbol = NumberButtonType.Four,
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(button4) {
                    start.linkTo(parent.start)
                    top.linkTo(secondSpacer.bottom)
                }
        ) {
            onNumberClick(NumberButtonType.Four)
        }
        NumberButton(
            symbol = NumberButtonType.Five,
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(button5) {
                    top.linkTo(button4.top)
                    bottom.linkTo(button4.bottom)
                }
        ) {
            onNumberClick(NumberButtonType.Five)
        }
        NumberButton(
            symbol = NumberButtonType.Six,
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(button6) {
                    top.linkTo(button4.top)
                    bottom.linkTo(button4.bottom)
                }
        ) {
            onNumberClick(NumberButtonType.Six)
        }
        ActionButton(
            symbol = ActionButtonType.Subtract,
            colorBackground = MaterialTheme.myColors.operationButtonColor,
            colorFont = MaterialTheme.myColors.fontColorPurple,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(buttonMin) {
                    top.linkTo(button4.top)
                    bottom.linkTo(button4.bottom)
                    end.linkTo(parent.end)
                }
        ) {
            onAction(ActionButtonType.Subtract)
        }
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .constrainAs(thirdSpacer) {
                    start.linkTo(parent.start)
                    top.linkTo(button4.bottom)
                    end.linkTo(parent.end)
                })
        NumberButton(
            symbol = NumberButtonType.One,
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(button1) {
                    start.linkTo(parent.start)
                    top.linkTo(thirdSpacer.bottom)
                }
        ) {
            onNumberClick(NumberButtonType.One)
        }
        NumberButton(
            symbol = NumberButtonType.Two,
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(button2) {
                    top.linkTo(button1.top)
                    bottom.linkTo(button1.bottom)
                }
        ) {
            onNumberClick(NumberButtonType.Two)
        }
        NumberButton(
            symbol = NumberButtonType.Three,
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(button3) {
                    top.linkTo(button1.top)
                    bottom.linkTo(button1.bottom)
                }
        ) {
            onNumberClick(NumberButtonType.Three)
        }
        ActionButton(
            symbol = ActionButtonType.Add,
            colorBackground = MaterialTheme.myColors.operationButtonColor,
            colorFont = MaterialTheme.myColors.fontColorPurple,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(buttonSum) {
                    top.linkTo(button1.top)
                    bottom.linkTo(button1.bottom)
                    end.linkTo(parent.end)
                }
        ) {
            onAction(ActionButtonType.Add)
        }
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .constrainAs(fourthSpacer) {
                    start.linkTo(parent.start)
                    top.linkTo(button1.bottom)
                    end.linkTo(parent.end)
                })
        NumberButton(
            symbol = NumberButtonType.Zero,
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(140.dp)
                .constrainAs(button0) {
                    start.linkTo(parent.start)
                    top.linkTo(fourthSpacer.bottom)
                }
        ) {
            onNumberClick(NumberButtonType.Zero)
        }
        NumberButton(
            symbol = NumberButtonType.Period,
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(buttonDot) {
                    top.linkTo(button0.top)
                    bottom.linkTo(button0.bottom)
                }
        ) {
            onNumberClick(NumberButtonType.Period)
        }
        ActionButton(
            symbol = ActionButtonType.BackSpace,
            colorBackground = MaterialTheme.myColors.operationButtonColor,
            colorFont = MaterialTheme.myColors.fontColorPurple,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(buttonSquare) {
                    top.linkTo(button0.top)
                    bottom.linkTo(button0.bottom)
                }
        ) {
            onAction(ActionButtonType.BackSpace)
        }
        ActionButton(
            symbol = ActionButtonType.Delete,
            colorBackground = MaterialTheme.myColors.operationButtonColor,
            colorFont = MaterialTheme.myColors.fontColorPurple,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(buttonDel) {
                    top.linkTo(fifthSpacer.bottom)
                    start.linkTo(parent.start)
                }
        ) {
            onAction(ActionButtonType.Delete)
        }
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .constrainAs(fifthSpacer) {
                    start.linkTo(parent.start)
                    top.linkTo(button0.bottom)
                    end.linkTo(parent.end)
                })
        ActionButton(
            symbol = ActionButtonType.Evaluate,
            colorBackground = MaterialTheme.myColors.calculateButtonColor,
            colorFont = Color.White,
            modifier = Modifier
                .width(210.dp)
                .constrainAs(buttonEqual) {
                    end.linkTo(parent.end)
                    top.linkTo(buttonDel.top)
                    bottom.linkTo(buttonDel.bottom)
                }
        ) {
            onAction(ActionButtonType.Evaluate)
        }
    }
}

@Preview
@Composable
private fun CalculateScreen() {
    CalculatorScreen(
        modifier = Modifier.fillMaxWidth(),
        state = UICalculateState(
            input = "123",
            pendingValue = BigDecimal(456),
            pendingOperation = ActionButtonType.Add
        )
    )


}