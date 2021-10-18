package com.z3r08ug.chillspace

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.z3r08ug.chillspace.ui.theme.ChillSpaceTheme
import com.z3r08ug.chillspace.ui.theme.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.LoginScreen.route
                ) {
                    composable(Screen.LoginScreen.route) { navBackStackEntry ->
                        LoginScreen(navController, navBackStackEntry.arguments)
                    }
                }
            }
        }
    }
}