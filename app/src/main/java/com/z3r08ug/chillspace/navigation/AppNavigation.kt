package com.z3r08ug.chillspace.navigation//package com.z3r08ug.chillspace.navigation
//
//import androidx.compose.animation.ExperimentalAnimationApi
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//
//object AppNavigation {
//    @Composable
//    @OptIn(ExperimentalAnimationApi::class)
//    fun Host(navController: NavHostController) {
//        ElementsNavHost(
//            navHostController = navController,
//            startDestination = AppDestination.splash()
//        ) {
//            prefaceGraph(navController).builder(this)
//
//            graphs(appGraphs(navController))
//            graphs(accountGraphs(navController))
//            graphs(netWorthGraphs(navController))
//            graphs(peopleGraphs(navController))
//            graphs(elementsGraphs(navController))
//        }
//    }
//}
//
//object AppDestination {
//    fun splash(): String {
//        return PrefaceDestination.Splash.route()
//    }
//
//    fun welcome(): String {
//        return PrefaceDestination.Welcome.route()
//    }
//
//    fun home(): String {
//        return OverviewDestination.Home.route()
//    }
//
//    fun prompts(): String {
//        return PromptDestination.Prompts.route()
//    }
//
//    fun settings(): String {
//        return SettingsDestination.Settings.route()
//    }
//
//    fun aboutLegal(type: LegalType): String {
//        return AboutDestination.Legal.route(type)
//    }
//}
//
//fun appGraphs(navController: NavController): List<GraphProvider> {
//    return listOf(
//        overviewGraph(navController),
//        promptGraph(navController),
//        settingsGraph(navController),
//        aboutGraph(navController)
//    )
//}