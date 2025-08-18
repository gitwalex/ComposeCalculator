package com.gerwalex.calculator.arithmatic


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.gerwalex.calculator.common.NumberButton
import com.gerwalex.calculator.ui.component.myColors
import com.gerwalex.calculator.ui.theme.lightFontYellow

@Composable
fun CalculatorScreen(
    modifier: Modifier,
    calculateViewModel: CalculateViewModel,
) {
    CalculatorScreen(
        modifier = modifier,
        uiCalculateState = calculateViewModel.uiCalculateState,
        onAction = calculateViewModel::onAction
    )
}

@Composable
fun CalculatorScreen(
    modifier: Modifier,
    uiCalculateState: UICalculateState,
    onAction: (CalculateAction) -> Unit
) {
    val contentWidthCommon = 70.dp
    ConstraintLayout(
        modifier = modifier
            .background(Color.LightGray)
//            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp)
    ) {
        val (firstSpacer, secondSpacer, inputText, outputText, buttonC, buttonOpenBracket, buttonClosedBracket, buttonX, buttonDivide, button7, button8, button9, button4, button5, button6, buttonMin) = createRefs()
        val (thirdSpacer, button1, button2, button3, buttonSum, button0, buttonSquare, buttonDel, buttonEqual, buttonDot, fourthSpacer, fifthSpacer, sixSpacer) = createRefs()

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
                .fillMaxWidth()
                .constrainAs(inputText) {
                    top.linkTo(guidelineTop)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = uiCalculateState.input, textAlign = TextAlign.End,
            fontWeight = FontWeight.Black, fontSize = 20.sp
        )

        Text(
            text = uiCalculateState.result,
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
        NumberButton(
            symbol = "C",
            colorBackground = MaterialTheme.myColors.clearButtonColor,
            colorFont = Color.White,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(buttonC) {
                    start.linkTo(parent.start)
                    top.linkTo(sixSpacer.bottom)
                }
        ) {
            onAction(CalculateAction.ClearInput)
        }
        NumberButton(
            symbol = "(",
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = lightFontYellow,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(buttonOpenBracket) {
                    top.linkTo(buttonC.top)
                    bottom.linkTo(buttonC.bottom)
                }
        ) {
            onAction(CalculateAction.InputChange("("))
        }
        NumberButton(
            symbol = ")",
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = lightFontYellow,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(buttonClosedBracket) {
                    top.linkTo(buttonC.top)
                    bottom.linkTo(buttonC.bottom)
                }
        ) {
            onAction(CalculateAction.InputChange(")"))
        }
        NumberButton(
            symbol = "÷",
            colorBackground = MaterialTheme.myColors.operationButtonColor,
            colorFont = MaterialTheme.myColors.fontColorPurple,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(buttonDivide) {
                    top.linkTo(buttonC.top)
                    bottom.linkTo(buttonC.bottom)
                    end.linkTo(parent.end)
                }) {
            onAction(CalculateAction.InputChange("/"))
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
            symbol = "7",
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(button7) {
                    start.linkTo(parent.start)
                    top.linkTo(firstSpacer.bottom)
                }
        ) {
            onAction(CalculateAction.InputChange("7"))
        }
        NumberButton(
            symbol = "8",
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(button8) {
                    top.linkTo(button7.top)
                    bottom.linkTo(button7.bottom)
                }
        ) {
            onAction(CalculateAction.InputChange("8"))
        }
        NumberButton(
            symbol = "9",
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(button9) {
                    bottom.linkTo(button7.bottom)
                    top.linkTo(button7.top)
                }
        ) {
            onAction(CalculateAction.InputChange("9"))
        }
        NumberButton(
            symbol = "x",
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
            onAction(CalculateAction.InputChange("*"))
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
            symbol = "4",
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(70.dp)
                .constrainAs(button4) {
                    start.linkTo(parent.start)
                    top.linkTo(secondSpacer.bottom)
                }
        ) {
            onAction(CalculateAction.InputChange("4"))
        }
        NumberButton(
            symbol = "5",
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(button5) {
                    top.linkTo(button4.top)
                    bottom.linkTo(button4.bottom)
                }
        ) {
            onAction(CalculateAction.InputChange("5"))
        }
        NumberButton(
            symbol = "6",
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(button6) {
                    top.linkTo(button4.top)
                    bottom.linkTo(button4.bottom)
                }
        ) {
            onAction(CalculateAction.InputChange("6"))
        }
        NumberButton(
            symbol = "-",
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
            onAction(CalculateAction.InputChange("-"))
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
            symbol = "1",
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(button1) {
                    start.linkTo(parent.start)
                    top.linkTo(thirdSpacer.bottom)
                }
        ) {
            onAction(CalculateAction.InputChange("1"))
        }
        NumberButton(
            symbol = "2",
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(button2) {
                    top.linkTo(button1.top)
                    bottom.linkTo(button1.bottom)
                }
        ) {
            onAction(CalculateAction.InputChange("2"))
        }
        NumberButton(
            symbol = "3",
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(button3) {
                    top.linkTo(button1.top)
                    bottom.linkTo(button1.bottom)
                }
        ) {
            onAction(CalculateAction.InputChange("3"))
        }
        NumberButton(
            symbol = "+",
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
            onAction(CalculateAction.InputChange("+"))
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
            symbol = "0",
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(140.dp)
                .constrainAs(button0) {
                    start.linkTo(parent.start)
                    top.linkTo(fourthSpacer.bottom)
                }
        ) {
            onAction(CalculateAction.InputChange("0"))
        }
        NumberButton(
            symbol = ".",
            colorBackground = MaterialTheme.myColors.numberButtonColor,
            colorFont = MaterialTheme.myColors.fontColorYellow,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(buttonDot) {
                    top.linkTo(button0.top)
                    bottom.linkTo(button0.bottom)
                }
        ) {
            onAction(CalculateAction.InputChange("."))
        }
        NumberButton(
            symbol = "^",
            colorBackground = MaterialTheme.myColors.operationButtonColor,
            colorFont = MaterialTheme.myColors.fontColorPurple,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(buttonSquare) {
                    top.linkTo(button0.top)
                    bottom.linkTo(button0.bottom)
                }
        ) {
            onAction(CalculateAction.InputChange("^"))
        }
        NumberButton(
            symbol = "←",
            colorBackground = MaterialTheme.myColors.operationButtonColor,
            colorFont = MaterialTheme.myColors.fontColorPurple,
            modifier = Modifier
                .width(contentWidthCommon)
                .constrainAs(buttonDel) {
                    top.linkTo(fifthSpacer.bottom)
                    start.linkTo(parent.start)
                }
        ) {
            onAction(CalculateAction.Delete)
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
        NumberButton(
            symbol = "=",
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
            onAction(CalculateAction.Evaluate)
        }
    }
}

@Preview
@Composable
private fun CalculateScreen() {
    CalculatorScreen(
        modifier = Modifier.fillMaxWidth(),
        calculateViewModel = CalculateViewModel()
    )


}