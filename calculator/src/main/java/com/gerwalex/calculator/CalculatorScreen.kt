package com.gerwalex.calculator


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gerwalex.calculator.arithmatic.CalculatorBrain
import com.gerwalex.calculator.arithmatic.UICalculateState
import com.gerwalex.calculator.common.ActionButtonType
import com.gerwalex.calculator.ui.component.myColors
import java.math.BigDecimal


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorDialog(
    modifier: Modifier = Modifier,
    settings: CalculatorSettings = CalculatorSettings(),
    properties: DialogProperties = DialogProperties(),
    onResult: (BigDecimal) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val brain: CalculatorBrain = viewModel()
    val state by brain.state.collectAsStateWithLifecycle()
    LaunchedEffect(brain) {
        brain.setup(settings)
    }
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        properties = properties,
        content = {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                tonalElevation = 6.dp, // M3 Effekt für Tiefe
                color = MaterialTheme.myColors.backgroundColor, // <--- DEIN HINTERGRUND
                contentColor = MaterialTheme.myColors.fontColorPurple // Standard-Textfarbe
            ) {
                Column(
                    modifier = Modifier.widthIn(max = 500.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CalculatorLayout(
                        state = state,
                        onAction = { brain.onAction(it) },
                        onNumber = { brain.onNumberAction(it) })
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = { onDismissRequest() }) {
                            Text("Abbrechen")
                        }
                        TextButton(onClick = { onResult(state.input) }) {
                            Text("Speichern")
                        }

                    }
                }
            }
        })
}

@Preview
@Composable
private fun CalculateScreen() {
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

@Preview
@Composable
private fun CalculateDialog() {
    val settings = CalculatorSettings(initialValue = BigDecimal(12345))
    CalculatorDialog(
        modifier = Modifier.fillMaxWidth(),
        settings = settings,
        onResult = {},
        onDismissRequest = {}

    )
}
