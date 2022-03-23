package com.z3r0_8ug.ui_common.framework.ext

import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.require(key: String): T {
  return get<T>(key) ?: throw IllegalArgumentException("Argument \"${key}\" must be defined")
}