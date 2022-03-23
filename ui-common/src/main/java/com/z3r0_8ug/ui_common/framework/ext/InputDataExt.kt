package com.z3r0_8ug.ui_common.framework.ext

import com.z3r0_8ug.ui_common.framework.ui.InputData
import timber.log.Timber

fun <T> emptyInput(): InputData<T?> {
  return InputData(
    value = null,
    onValueChange = {
      Timber.w("Attempting to update emptyInput with value $it")
    }
  )
}