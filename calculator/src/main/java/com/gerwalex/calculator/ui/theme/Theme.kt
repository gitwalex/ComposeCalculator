package com.gerwalex.calculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.gerwalex.calculator.ui.component.CustomButtonColorGuideline

private val LightColorScheme = CustomButtonColorGuideline(
    colorScheme = lightColorScheme(),
    clearButtonColor = lightYellow,
    numberButtonColor = lightTransparentYellow,
    operationButtonColor = lightTransparentPurple,
    calculateButtonColor = lightPurple,
    backgroundColor = lightBackground,
    fontColorPurple = lightFontPurple,
    fontColorYellow = lightFontYellow
)

private val DarkColorScheme = CustomButtonColorGuideline(
    colorScheme = darkColorScheme(),
    clearButtonColor = darkYellow,
    numberButtonColor = darkTransparentYellow,
    operationButtonColor = darkTransparentPurple,
    calculateButtonColor = darkPurple,
    backgroundColor = darkBackground,
    fontColorPurple = darkFontPurple,
    fontColorYellow = darkFontYellow
)
val LocalColors = staticCompositionLocalOf { LightColorScheme }

@Composable
fun CalculatorAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    CompositionLocalProvider(LocalColors provides colorScheme) {
        MaterialTheme(
            colorScheme = colorScheme.colorScheme,
            typography = Typography,
            content = content
        )
    }
}