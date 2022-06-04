package com.z3r0_8ug.ui_common.ext

import android.graphics.Typeface
import android.os.Build
import android.text.TextPaint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import kotlin.math.absoluteValue

internal data class DrawTextStyle(
  val color: Color,
  val density: Density,
  val fontSize: TextUnit,
  val centerVertically: Boolean = true,
  val fontStyle: FontStyle? = null,
  val fontWeight: FontWeight? = null,
  val letterSpacing: TextUnit? = null,
) {
  private val typeface by lazy {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      Typeface.create(
        null as Typeface?,
        fontWeight?.weight ?: FontWeight.Normal.weight,
        fontStyle == FontStyle.Italic
      )
    } else {
      // Flag, 0 is normal
      var style = 0

      if (fontWeight != null && fontWeight.weight >= FontWeight.Bold.weight) {
        style = style or 1
      }

      if (fontStyle == FontStyle.Italic) {
        style = style or 2
      }

      Typeface.create(
        null as Typeface?,
        style
      )
    }
  }

  val nativePaint by lazy {
    TextPaint().apply {
      this.density = this@DrawTextStyle.density.density
      this.color = this@DrawTextStyle.color.toArgb()
      this.textSize = with(this@DrawTextStyle.density) { fontSize.toPx() }
      this.typeface = this@DrawTextStyle.typeface
      this@DrawTextStyle.letterSpacing?.let {
        this.letterSpacing = it.toEm(this@DrawTextStyle.density)
      }

      isAntiAlias = true
      isDither = true
    }
  }
}

internal fun DrawScope.drawText(
  text: String,
  position: Offset,
  style: DrawTextStyle
) = drawIntoCanvas {
  val yDelta = if (style.centerVertically) {
    val textHeight = style.nativePaint.descent() - style.nativePaint.ascent()
    style.nativePaint.ascent().absoluteValue - (textHeight / 2f)
  } else {
    0f
  }

  it.nativeCanvas.drawText(text, position.x, position.y + yDelta, style.nativePaint)
}