package com.z3r0_8ug.ui_common.framework.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder

interface NavDestination {
  val routeFormat: String
  val routeArguments: List<NamedNavArgument>
}

data class GraphProvider(
  val startDestination: NavDestination,
  val route: String,
  val builder: NavGraphBuilder.() -> Unit
)

fun routeWithParams(route: String, vararg params: String?): String {
  return routeWithParams(route, params.toList())
}

fun routeWithParams(route: String, params: List<String?>): String {
  val nonNullParams = params.filterNotNull()
  if (nonNullParams.isEmpty()) {
    return route
  }

  var hasFirstParam = route.contains("?")
  return StringBuilder(route).apply {
    params.forEach { param ->
      if (!hasFirstParam) {
        append("?$param")
        hasFirstParam = true
      } else {
        append("&$param")
      }
    }
  }.toString()
}

fun routeFormat(route: String, args: List<NamedNavArgument>): String {
  return routeWithParams(
    route,
    args.map { "${it.name}={${it.name}}" }
  )
}