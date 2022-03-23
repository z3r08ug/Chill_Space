package com.z3r0_8ug.ui_common.framework.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun ChillSpaceNavHost(
  navHostController: NavHostController,
  startDestination: String,
  route: String? = null,
  builder: NavGraphBuilder.() -> Unit
) {
  val startDestinationWithTransition = routeWithParams(startDestination, NavigationTransition.argFormat)

  AnimatedNavHost(
    navController = navHostController,
    startDestination = startDestinationWithTransition,
    route = route,
    enterTransition = { XAxisTransition.enter },
    exitTransition = { XAxisTransition.exit },
    popEnterTransition = { XAxisTransition.popEnter },
    popExitTransition = { XAxisTransition.popExit },
    builder = builder
  )
}