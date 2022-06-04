package com.z3r0_8ug.ui_common.framework.ext

import androidx.annotation.PluralsRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext

@Composable
@ReadOnlyComposable
fun pluralStringResource(@PluralsRes id: Int, quantity: Int): String {
  val resources = LocalContext.current.resources
  return resources.getQuantityString(id, quantity)
}

@Composable
@ReadOnlyComposable
fun pluralStringResource(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String {
  val resources = LocalContext.current.resources
  return resources.getQuantityString(id, quantity, *formatArgs)
}