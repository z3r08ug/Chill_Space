package com.getelements.elements.ui.framework.util

import kotlinx.coroutines.Dispatchers

open class Dispatchers {
  val main = Dispatchers.Main
  open val default = Dispatchers.Default
  open val io = Dispatchers.IO
  open val unconfined = Dispatchers.Unconfined
}