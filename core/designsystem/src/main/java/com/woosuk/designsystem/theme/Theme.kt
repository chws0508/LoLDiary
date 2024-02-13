package com.woosuk.loldiary.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.woosuk.designsystem.theme.backgroundDark
import com.woosuk.designsystem.theme.backgroundLight
import com.woosuk.designsystem.theme.errorContainerDark
import com.woosuk.designsystem.theme.errorContainerLight
import com.woosuk.designsystem.theme.errorDark
import com.woosuk.designsystem.theme.errorLight
import com.woosuk.designsystem.theme.inverseOnSurfaceDark
import com.woosuk.designsystem.theme.inverseOnSurfaceLight
import com.woosuk.designsystem.theme.inversePrimaryDark
import com.woosuk.designsystem.theme.inversePrimaryLight
import com.woosuk.designsystem.theme.inverseSurfaceDark
import com.woosuk.designsystem.theme.inverseSurfaceLight
import com.woosuk.designsystem.theme.onBackgroundDark
import com.woosuk.designsystem.theme.onBackgroundLight
import com.woosuk.designsystem.theme.onErrorContainerDark
import com.woosuk.designsystem.theme.onErrorContainerLight
import com.woosuk.designsystem.theme.onErrorDark
import com.woosuk.designsystem.theme.onErrorLight
import com.woosuk.designsystem.theme.onPrimaryContainerDark
import com.woosuk.designsystem.theme.onPrimaryContainerLight
import com.woosuk.designsystem.theme.onPrimaryDark
import com.woosuk.designsystem.theme.onPrimaryLight
import com.woosuk.designsystem.theme.onSecondaryContainerDark
import com.woosuk.designsystem.theme.onSecondaryContainerLight
import com.woosuk.designsystem.theme.onSecondaryDark
import com.woosuk.designsystem.theme.onSecondaryLight
import com.woosuk.designsystem.theme.onSurfaceDark
import com.woosuk.designsystem.theme.onSurfaceLight
import com.woosuk.designsystem.theme.onSurfaceVariantDark
import com.woosuk.designsystem.theme.onSurfaceVariantLight
import com.woosuk.designsystem.theme.onTertiaryContainerDark
import com.woosuk.designsystem.theme.onTertiaryContainerLight
import com.woosuk.designsystem.theme.onTertiaryDark
import com.woosuk.designsystem.theme.onTertiaryLight
import com.woosuk.designsystem.theme.outlineDark
import com.woosuk.designsystem.theme.outlineLight
import com.woosuk.designsystem.theme.outlineVariantDark
import com.woosuk.designsystem.theme.outlineVariantLight
import com.woosuk.designsystem.theme.primaryContainerDark
import com.woosuk.designsystem.theme.primaryContainerLight
import com.woosuk.designsystem.theme.primaryDark
import com.woosuk.designsystem.theme.primaryLight
import com.woosuk.designsystem.theme.scrimDark
import com.woosuk.designsystem.theme.scrimLight
import com.woosuk.designsystem.theme.secondaryContainerDark
import com.woosuk.designsystem.theme.secondaryContainerLight
import com.woosuk.designsystem.theme.secondaryDark
import com.woosuk.designsystem.theme.secondaryLight
import com.woosuk.designsystem.theme.surfaceDark
import com.woosuk.designsystem.theme.surfaceLight
import com.woosuk.designsystem.theme.surfaceVariantDark
import com.woosuk.designsystem.theme.surfaceVariantLight
import com.woosuk.designsystem.theme.tertiaryContainerDark
import com.woosuk.designsystem.theme.tertiaryContainerLight
import com.woosuk.designsystem.theme.tertiaryDark
import com.woosuk.designsystem.theme.tertiaryLight

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceTint = surfaceLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceTint = surfaceDark,
)

@Composable
fun LoLDiaryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable() () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
