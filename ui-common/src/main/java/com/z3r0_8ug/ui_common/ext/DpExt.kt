package com.z3r0_8ug.ui_common.ext

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

fun Dp.toPx(density: Density): Float {
  return with(density) {
    toPx()
  }
}