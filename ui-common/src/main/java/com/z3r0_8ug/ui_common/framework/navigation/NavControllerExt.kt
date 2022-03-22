package com.z3r0_8ug.ui_common.framework.navigation

import androidx.navigation.*

/**
 * Handles the navigation action, including the [NavigationTransition.Type] that should be used
 * when pushing and popping the [route]
 */
fun NavController.navigate(
  route: String,
  transitionType: NavigationTransition.Type,
  navOptions: NavOptions? = null,
  navigatorExtras: Navigator.Extras? = null
) {
  val routeWithTransition = routeWithParams(route, "${NavigationTransition.argName}=${transitionType}")

  navigate(
    route = routeWithTransition,
    navOptions = navOptions,
    navigatorExtras = navigatorExtras
  )
}

fun <T> NavController.popBackStackWithResult(key: String, value: T): Boolean {
  previousBackStackEntry?.savedStateHandle?.set(key, value)
  return popBackStack()
}

/**
 * Pops the current graph from the backstack.
 *
 * @param inclusive `true` if the graph itself should be popped
 */
fun NavController.popGraph(inclusive: Boolean = true): Boolean {
  currentBackStackEntry?.destination?.parent?.route?.let { route ->
    return popBackStack(route, inclusive)
  }

  return false
}

@Suppress("FoldInitializerAndIfToElvis")
fun NavOptionsBuilder.popUpToGraph(controller: NavController, inclusive: Boolean = true) {
  val parent = controller.currentBackStackEntry?.destination?.parent
  val graphRoute = parent?.route ?: parent?.startDestinationRoute
  val route = graphRoute ?: controller.currentBackStackEntry?.destination?.route

  route?.let {
    popUpTo(it) {
      this.inclusive = inclusive
    }
  }
}

@Suppress("FoldInitializerAndIfToElvis")
fun NavOptionsBuilder.popUpToAll(controller: NavController) {
  // NOTE:
  // I don't see a way to pop the root NavGraph from the stack, however
  // it doesn't seem to cause a problem so we just leave it and find the
  // first actual destination
  val first = controller.backQueue.firstOrNull {
    it.destination !is NavGraph
  }?.destination

  first?.route?.let {
    popUpTo(it) {
      inclusive = true
    }
  }
}

/**
 * Pops *all* graphs defined by [graphRoutes], including duplicates.
 * If any graphs are found between the defined [graphRoutes], those
 * undefined graphs will also be popped.
 */
fun NavController.popGraphs(vararg graphRoutes: String): Boolean {
  // Maps the graphs to the backStack index
  val indexMap = graphRoutes.associateWith { -1 }.toMutableMap()
  backQueue.forEachIndexed { index, entry ->
    val destination = entry.destination
    if (destination is NavGraph) {
      destination.route?.let {
        if (indexMap.containsKey(it)) {
          val currentIndex = indexMap[it] ?: -1
          indexMap[it] = when {
            currentIndex <= 0 -> index
            else -> minOf(currentIndex, index)
          }
        }
      }
    }
  }

  val minIndex = indexMap.values.fold(-1) { acc, index ->
    when {
      acc <= 0 -> index
      index <= 0 -> acc
      else -> minOf(acc, index)
    }
  }

  if (minIndex <= 0) {
    return false
  }

  // Because there can be duplicate graphs on the back stack we need to just iteratively
  // pop until we reach our destination
  while (minIndex >= 0 && backQueue.size > minIndex) {
    if (!popBackStack()) {
      return false
    }
  }

  return true
}