package com.gerwalex.calculator


import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import com.gerwalex.calculator.ui.theme.CalculatorAppTheme
import com.gerwalex.calculator.ui.theme.CalculatorStyle
import com.gerwalex.calculator.ui.theme.CalculatorStyleDefaults
import java.math.BigDecimal


/**
 * A dialog-based calculator that allows users to perform arithmetic operations and return a result.
 *
 * @param modifier The [Modifier] to be applied to the dialog.
 * @param settings Configuration settings for the calculator, such as initial values and precision.
 * @param colors The [CalculatorStyle] defining the color scheme for the calculator UI.
 * @param properties [DialogProperties] for further customization of the dialog's behavior.
 * @param onResult Callback invoked when the user confirms the calculation (e.g., clicks "Speichern"),
 * providing the resulting [BigDecimal].
 * @param onDismissRequest Callback invoked when the user requests to dismiss the dialog (e.g., clicks "Abbrechen").
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorDialog(
    modifier: Modifier = Modifier,
    settings: CalculatorSettings = CalculatorSettings(),
    colors: CalculatorStyle = CalculatorStyleDefaults.defaultColors(),
    properties: DialogProperties = DialogProperties(dismissOnClickOutside = false),
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
                tonalElevation = 6.dp,
                color = colors.backgroundColor,
            ) {
                Column(
                    modifier = Modifier.widthIn(max = 500.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CalculatorLayout(
                        state = state,
                        colors = colors,
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
fun CalculatorScreenPreview() {
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

@Preview(uiMode = UI_MODE_NIGHT_NO, name = "Dialog-Day")
@Preview(uiMode = UI_MODE_NIGHT_YES, name = "Dialog-Night")
@Composable
private fun CalculateDialog() {
    CalculatorAppTheme {
        val settings = CalculatorSettings(initialValue = BigDecimal(12345))
        CalculatorDialog(
            modifier = Modifier.fillMaxWidth(),
            settings = settings,
            onResult = {},
            onDismissRequest = {}

        )
    }
}
