package com.z3r0_8ug.ui_common.ext

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

@Stable
fun TextUnit.toEm(density: Density): Float {
  if (type == TextUnitType.Em) {
    return value
  }

  return value / (density.density * density.fontScale)
}
