package com.z3r0_8ug.ui_common.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.z3r0_8ug.ui_common.theme.color.AppColors
import com.z3r0_8ug.ui_common.theme.color.LocalAppColors
import com.z3r0_8ug.ui_common.theme.color.darkColors
import com.z3r0_8ug.ui_common.theme.color.lightColors
import com.z3r0_8ug.ui_common.theme.shape.AppShapes
import com.z3r0_8ug.ui_common.theme.shape.LocalAppShapes
import com.z3r0_8ug.ui_common.theme.shape.defaultShapes
import com.z3r0_8ug.ui_common.theme.text.AppTypography
import com.z3r0_8ug.ui_common.theme.text.LocalAppTypography
import com.z3r0_8ug.ui_common.theme.text.defaultTypography

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

@SuppressLint("ComposableNaming")
@Composable
fun AppTheme.withSecondaryColor(color: Color, content: @Composable () -> Unit) {
  AppTheme(
    colors = colors.copy(
      secondary = color
    ),
    content = content
  )
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