package com.z3r0_8ug.ui_common.framework.ext

import com.google.accompanist.insets.Insets
import com.google.accompanist.insets.WindowInsets

val WindowInsets.navigationBarsWithIme: Insets
  get() {
    return navigationBars + ime
  }

