package com.example.wavetable_synthesizer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = specialOrange,
    primaryVariant = specialDarkOrange,
    secondary = specialGray
)

private val LightColorPalette = lightColors(
    primary = specialOrange,
    primaryVariant = specialDarkOrange,
    secondary = specialGray
)

@Composable
fun WavetablesythesizerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}