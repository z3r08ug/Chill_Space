package com.z3r08ug.chillspace

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember

object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current
}

@Composable
fun AppTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    colors: AppColors = if (isDark) { darkColors } else { lightColors },
    typography: AppTypography = defaultTypography,
    shapes: AppShapes = defaultShapes,
    content: @Composable () -> Unit
) {
    val rememberedColors = remember { colors }
    val rememberedTypography = remember { typography }
    val rememberedShapes = remember { shapes }

    CompositionLocalProvider(
        LocalAppColors provides rememberedColors,
        LocalAppTypography provides rememberedTypography,
        LocalAppShapes provides rememberedShapes
    ) {
        MaterialTheme(
            colors = LocalAppColors.current.toMaterialColors(isDark),
            typography = LocalAppTypography.current.toMaterialTypography(),
            shapes = LocalAppShapes.current.toMaterialShapes(),
            content = content
        )
    }
}

private fun AppColors.toMaterialColors(isDark: Boolean) = Colors(
    primary = primary,
    primaryVariant = primaryVariant,
    secondary = secondary,
    secondaryVariant = secondary,
    background = background,
    surface = surface,
    error = error,
    onPrimary = text80,
    onSecondary = darkGray,
    onBackground = text80,
    onSurface = darkGray,
    onError = text100,
    isLight = !isDark
)

private fun AppTypography.toMaterialTypography() = Typography(
    defaultFontFamily = fontFamily,
    h1 = h1,
    h2 = h2,
    h3 = h3,
    h4 = h4,
    h5 = h5,
    h6 = h6,
    subtitle1 = subtitle1,
    subtitle2 = subtitle2,
    body1 = body1,
    body2 = body2,
    button = button,
    caption = caption,
    overline = overline
)

private fun AppShapes.toMaterialShapes() = Shapes(
    small = small,
    medium = medium,
    large = large
)