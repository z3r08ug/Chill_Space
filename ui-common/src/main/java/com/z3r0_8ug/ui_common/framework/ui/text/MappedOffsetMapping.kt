package com.z3r0_8ug.ui_common.framework.ui.text

import androidx.compose.ui.text.input.OffsetMapping

class MappedOffsetMapping(
  private val originalToTransformedOffsetMap: Map<Int, TransformedOffsets>
) : OffsetMapping {
  override fun originalToTransformed(offset: Int): Int {
    return originalToTransformedOffsetMap[offset]?.minOffset ?: offset
  }

  override fun transformedToOriginal(offset: Int): Int {
    return originalToTransformedOffsetMap.entries.firstOrNull { it.value.contains(offset) }?.key ?: offset
  }

  data class TransformedOffsets(
    val offsets: List<Int>
  ) {
    val minOffset: Int by lazy {
      offsets.minOf { it }
    }

    fun contains(offset: Int): Boolean {
      return offsets.any { it == offset }
    }
  }
}