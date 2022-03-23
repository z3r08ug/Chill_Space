package com.z3r0_8ug.ui_common.ext

import java.time.LocalDate
import java.time.ZoneId

internal fun LocalDate.toEpochMilli(): Long {
  return atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}
