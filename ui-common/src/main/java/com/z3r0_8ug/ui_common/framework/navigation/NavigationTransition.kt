package com.z3r0_8ug.ui_common.framework.navigation

object NavigationTransition {
  internal const val argName = "navigationTransitionType"
  internal const val argFormat = "$argName={$argName}"

  internal val navArg = namedNavArg(
    name = argName,
    type = NavArgumentType.Enumeration(Type::class.java),
    nullable = true
  )

  enum class Type {
    X_AXIS,
    Z_AXIS
  }
}