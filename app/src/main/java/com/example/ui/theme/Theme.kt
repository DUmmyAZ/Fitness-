package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = ZoZoPurple,
    onPrimary = SurfaceWhite,
    primaryContainer = ZoZoPurpleContainer,
    onPrimaryContainer = ZoZoPurpleDark,
    secondary = ZoZoOrange,
    onSecondary = SurfaceWhite,
    secondaryContainer = ZoZoOrangeContainer,
    onSecondaryContainer = ZoZoOrangeDark,
    tertiary = ZoZoPurpleLight,
    onTertiary = SurfaceWhite,
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceOffWhite,
    onSurfaceVariant = TextSecondary,
    outline = SurfaceBorder,
    outlineVariant = SurfaceBorderSubtle
)

private val DarkColorScheme = darkColorScheme(
    primary = ZoZoPurpleLight,
    onPrimary = SurfaceWhite,
    primaryContainer = ZoZoPurpleDark,
    onPrimaryContainer = ZoZoPurpleContainer,
    secondary = ZoZoOrangeLight,
    onSecondary = SurfaceWhite,
    secondaryContainer = ZoZoOrangeDark,
    onSecondaryContainer = ZoZoOrangeContainer,
    tertiary = ZoZoPurple,
    onTertiary = SurfaceWhite,
    background = Color(0xFF121418),
    onBackground = Color(0xFFF1F3F5),
    surface = Color(0xFF1B1E24),
    onSurface = Color(0xFFF1F3F5),
    surfaceVariant = Color(0xFF232730),
    onSurfaceVariant = Color(0xFFB0B6C2),
    outline = Color(0xFF323846)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ZoZoTypography,
        content = content
    )
}
