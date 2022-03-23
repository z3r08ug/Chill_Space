package com.z3r0_8ug.ui_common.ext

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.material.placeholder
import com.z3r0_8ug.ui_common.theme.AppTheme

@Composable
fun Modifier.animatedPlaceholder(
  visible: Boolean,
  color: Color = AppTheme.colors.placeholder,
  shape: Shape = AppTheme.shapes.small,
  highlight: PlaceholderHighlight = PlaceholderHighlight.fade(
    AppTheme.colors.background.copy(alpha = 0.3f),
    animationSpec = infiniteRepeatable(
      animation = tween(
        delayMillis = 200,
        durationMillis = 600,
        easing = FastOutLinearInEasing
      ),
      repeatMode = RepeatMode.Reverse,
    )
  )
) = placeholder(
  visible = visible,
  color = color,
  shape = shape,
  highlight = highlight
)