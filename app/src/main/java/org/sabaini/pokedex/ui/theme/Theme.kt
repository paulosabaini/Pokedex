package org.sabaini.pokedex.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColorScheme(
    primary = Red,
    secondary = Red,
    background = Black,
    onPrimary = White,
    onSecondary = White,
)

private val LightColorPalette = lightColorScheme(
    primary = Red,
    secondary = Red,
    background = Black,
    onPrimary = White,
    onSecondary = White,
)

@Composable
fun PokedexTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(colors.primary)

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}
