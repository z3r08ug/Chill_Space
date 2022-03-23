package com.z3r0_8ug.ui_common.component.chart

import androidx.compose.ui.graphics.Color

data class ColorGradient(val colors: List<Color>) {
  init {
    if (colors.isEmpty() || colors.size > 3) {
      throw IllegalArgumentException("A ColorGradient requires between 1 and 3 colors")
    }
  }

  private val gradient by lazy {
    when (colors.size) {
      1 -> arrayOf(
        0f to colors.first(),
        1f to colors.first()
      )
      2 -> arrayOf(
        0f to colors.first(),
        1f to colors[1]
      )
      else -> arrayOf(
        0f to colors.first(),
        0.57f to colors[1],
        1f to colors[2],
      )
    }
  }

  fun gradientColors(): Array<Pair<Float, Color>> {
    return gradient
  }
}