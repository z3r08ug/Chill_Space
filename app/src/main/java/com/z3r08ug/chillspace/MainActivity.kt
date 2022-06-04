package com.z3r08ug.chillspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.firebase.auth.FirebaseAuth
import com.z3r08ug.chillspace.ui.login.NewLoginScreen
import com.z3r08ug.chillspace.ui.theme.ChillSpaceTheme
import com.z3r08ug.chillspace.ui.theme.LoginScreen
import com.z3r0_8ug.ui_common.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private var startRoute = Screen.NewLoginScreen.route

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startRoute = Screen.HomeScreen.route
        }

        setContent {
            ProvideWindowInsets(
                windowInsetsAnimationsEnabled = true
            ) {
                AppTheme() {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = startRoute
                    ) {
                        composable(Screen.LoginScreen.route) { navBackStackEntry ->
                            LoginScreen(
                                navController,
                                navBackStackEntry.arguments,
                                auth,
                                this@MainActivity
                            )
                        }
                        composable(Screen.NewLoginScreen.route) { navBackStackEntry ->
                            NewLoginScreen(
                                hiltViewModel(),
                                onClose = { navController.popBackStack() },
                                onComplete = { navController.popBackStack() },
                                onForgotPassword = {}
                            ) {}
                        }
                        composable(Screen.HomeScreen.route) { navBackStackEntry ->
                            HomeScreen(
                                navController,
                                navBackStackEntry.arguments,
                                currentUser,
                                this@MainActivity,
                                null,
                                null
                            )
                        }
                    }
                }
            }
        }
    }
}