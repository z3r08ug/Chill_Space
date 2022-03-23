package com.z3r0_8ug.ui_common.framework.ui

data class InputData<T>(
  val value: T,
  val onValueChange: (T) -> Unit,
  val hasError: Boolean = false,

  /**
   * Indicates if modifications sent to [onValueChange] will be accepted,
   * this will usually only be false when persisting an item or showing
   * a field that the user can't change.
   */
  val modificationsAllowed: Boolean = true,

  /**
   * Indicates if input will be accepted to this [InputData]. This should be
   * used to know weather an input should be displayed or not.
   */
  val inputAccepted: Boolean = true,

  /**
   * The value to use as the placeholder when no `value` is present. In most
   * cases the placeholder will be hard-coded in the `AppTextField` instance,
   * however in some cases it's beneficial to have a more dynamic placeholder
   * which is where this comes in to play.
   */
  val placeholder: T? = null
)
