package com.rivaldo.timeless.presentation.core

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * Soft, calming color palette for Timeless.
 * Uses gentle surface tones to create a non-anxious, reflective atmosphere.
 */

private val SoftSand = Color(0xFFF5F0EB)
private val SoftSandDark = Color(0xFF1C1B1A)
private val WarmBrown = Color(0xFF8B7355)
private val WarmBrownDark = Color(0xFFA68B6E)
private val SoftTeal = Color(0xFF5B8C8C)
private val SoftTealDark = Color(0xFF7AA8A8)
private val DeepTeal = Color(0xFF2C5F5F)
private val PalePeach = Color(0xFFF0E6D3)
private val PalePeachDark = Color(0xFF3D3529)
private val OffWhite = Color(0xFFFDFAF5)
private val OffBlack = Color(0xFF1A1A18)
private val GentleError = Color(0xFFC95C5C)
private val GentleErrorDark = Color(0xFFE07373)

private val LightColorScheme = lightColorScheme(
    primary = DeepTeal,
    onPrimary = Color.White,
    primaryContainer = SoftTeal.copy(alpha = 0.3f),
    onPrimaryContainer = DeepTeal,
    secondary = WarmBrown,
    onSecondary = Color.White,
    secondaryContainer = WarmBrown.copy(alpha = 0.2f),
    onSecondaryContainer = WarmBrown,
    tertiary = SoftTeal,
    onTertiary = Color.White,
    background = OffWhite,
    onBackground = OffBlack,
    surface = SoftSand,
    onSurface = OffBlack,
    surfaceVariant = PalePeach,
    onSurfaceVariant = Color(0xFF5C5547),
    error = GentleError,
    onError = Color.White,
    outline = Color(0xFFD4C9B8)
)

private val DarkColorScheme = darkColorScheme(
    primary = SoftTealDark,
    onPrimary = Color(0xFF003737),
    primaryContainer = DeepTeal.copy(alpha = 0.6f),
    onPrimaryContainer = Color(0xFFB3E0E0),
    secondary = WarmBrownDark,
    onSecondary = Color(0xFF3E2D1A),
    secondaryContainer = WarmBrown.copy(alpha = 0.3f),
    onSecondaryContainer = Color(0xFFE8D5BC),
    tertiary = SoftTealDark,
    onTertiary = Color(0xFF003737),
    background = OffBlack,
    onBackground = Color(0xFFE4E1DC),
    surface = SoftSandDark,
    onSurface = Color(0xFFE4E1DC),
    surfaceVariant = PalePeachDark,
    onSurfaceVariant = Color(0xFFCAC0B0),
    error = GentleErrorDark,
    onError = Color(0xFF601410),
    outline = Color(0xFF5C5547)
)

/**
 * Timeless Material 3 theme.
 * Applies soft, warm color scheme that promotes calmness and reflection.
 * Call this at the root of your composable hierarchy.
 */
@Composable
fun TimelessTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowInsetsControllerCompat(window, view)
            window.statusBarColor = colorScheme.surface.toArgb()
            insetsController.isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
