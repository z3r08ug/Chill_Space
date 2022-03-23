package com.z3r0_8ug.ui_common.framework.ui.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation

@OptIn(ExperimentalAnimationApi::class)
private inline fun <T> AnimatedContentScope<NavBackStackEntry>.getTransition(
  noinline delegate: (AnimatedContentScope<NavBackStackEntry>.() -> T?)?,
  isPop: Boolean = false,
  provider: (NavigationTransition.Type) -> T
): T? {
  val state = when {
    isPop -> initialState
    else -> targetState
  }

  val argumentValue = state.arguments?.get(NavigationTransition.argName)
  val transitionType = argumentValue?.toString()?.let {
    NavigationTransition.navArg.argument.type.parseValue(it)
  } as? NavigationTransition.Type

  return delegate?.let {
    it(this)
  } ?: transitionType?.let {
    provider(it)
  }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.destination(
    destination: NavDestination,
    enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
  val routeWithTransition = routeWithParams(destination.routeFormat, NavigationTransition.argFormat)
  val argumentsWithTransition = destination.routeArguments + NavigationTransition.navArg

  val animatedEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?) = {
    getTransition(enterTransition) {
      when (it) {
        NavigationTransition.Type.X_AXIS -> XAxisTransition.enter
        NavigationTransition.Type.Z_AXIS -> ZAxisTransition.enter
      }
    }
  }

  val animatedExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?) = {
    getTransition(exitTransition) {
      when (it) {
        NavigationTransition.Type.X_AXIS -> XAxisTransition.exit
        NavigationTransition.Type.Z_AXIS -> ZAxisTransition.exit
      }
    }
  }

  val animatedPopEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?) = {
    getTransition(popEnterTransition, true) {
      when (it) {
        NavigationTransition.Type.X_AXIS -> XAxisTransition.popEnter
        NavigationTransition.Type.Z_AXIS -> ZAxisTransition.popEnter
      }
    }
  }

  val animatedPopExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?) = {
    getTransition(popExitTransition, true) {
      when (it) {
        NavigationTransition.Type.X_AXIS -> XAxisTransition.popExit
        NavigationTransition.Type.Z_AXIS -> ZAxisTransition.popExit
      }
    }
  }

  composable(
    route = routeWithTransition,
    arguments = argumentsWithTransition,
    enterTransition = animatedEnterTransition,
    exitTransition = animatedExitTransition,
    popEnterTransition = animatedPopEnterTransition,
    popExitTransition = animatedPopExitTransition,
    content = content
  )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.graph(
    graphProvider: GraphProvider,
    enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition)? = null,
    exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition)? = null,
    popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition)? = enterTransition,
    popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition)? = exitTransition
) {
  val startDestinationWithTransition = routeWithParams(graphProvider.startDestination.routeFormat,
    NavigationTransition.argFormat
  )

  navigation(
    startDestination = startDestinationWithTransition,
    route = graphProvider.route,
    enterTransition = enterTransition,
    exitTransition = exitTransition,
    popEnterTransition = popEnterTransition,
    popExitTransition = popExitTransition,
    builder = graphProvider.builder
  )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.graphs(
    graphProviders: List<GraphProvider>,
    enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition)? = null,
    exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition)? = null,
    popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition)? = enterTransition,
    popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition)? = exitTransition
) {
  graphProviders.forEach { graph ->
    graph(
      graphProvider = graph,
      enterTransition = enterTransition,
      exitTransition = exitTransition,
      popEnterTransition = popEnterTransition,
      popExitTransition = popExitTransition
    )
  }
}