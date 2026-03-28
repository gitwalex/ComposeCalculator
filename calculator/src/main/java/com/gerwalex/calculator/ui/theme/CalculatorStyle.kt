package com.gerwalex.calculator.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class CalculatorStyle(
    val backgroundColor: Color,
    val actionButtonColor: Color,
    val numberButtonColor: Color,
    val displayBackgroundColor: Color,
    val operatorButtonColor: Color,
    val operatorButtonTextStyle: TextStyle,
    val numberButtonTextStyle: TextStyle,
    val actionButtonTextStyle: TextStyle,
    val displayTextStyle: TextStyle,
)

object CalculatorThemeDefaults {
    @Composable
    fun defaultColors() = CalculatorStyle(
        backgroundColor = MaterialTheme.colorScheme.surface,
        operatorButtonColor = MaterialTheme.colorScheme.primaryContainer,
        actionButtonColor = MaterialTheme.colorScheme.secondaryContainer,
        numberButtonColor = MaterialTheme.colorScheme.surfaceVariant,
        displayBackgroundColor = MaterialTheme.colorScheme.surface,
        actionButtonTextStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        operatorButtonTextStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        numberButtonTextStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        displayTextStyle = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        ),
    )
}