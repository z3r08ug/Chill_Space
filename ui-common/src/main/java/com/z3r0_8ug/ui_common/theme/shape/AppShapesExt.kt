package com.z3r0_8ug.ui_common.theme.shape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

val defaultShapes = AppShapes(
  xSmall = RoundedCornerShape(4.dp),
  small = RoundedCornerShape(8.dp),
  medium = RoundedCornerShape(20.dp),
  large = RoundedCornerShape(30.dp),
  pill = RoundedCornerShape(50),
  bottomSheet = RoundedCornerShape(
    topStart = 30.dp,
    topEnd = 30.dp
  )
)

internal val LocalAppShapes = staticCompositionLocalOf { defaultShapes }