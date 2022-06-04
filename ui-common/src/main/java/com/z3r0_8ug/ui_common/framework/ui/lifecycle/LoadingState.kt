package com.z3r0_8ug.ui_common.framework.ui.lifecycle

sealed class LoadingState<T, E> {
  class Loading<T, E>: LoadingState<T, E>()
  class Loaded<T, E>(val value: T): LoadingState<T, E>()
  class Failed<T, E>(val error: E): LoadingState<T, E>()
}