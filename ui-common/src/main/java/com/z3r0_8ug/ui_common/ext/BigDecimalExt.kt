package com.z3r0_8ug.ui_common.ext

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.round(): BigDecimal = this.setScale(0, RoundingMode.HALF_UP)