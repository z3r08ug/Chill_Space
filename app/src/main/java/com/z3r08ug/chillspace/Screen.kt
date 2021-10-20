package com.z3r08ug.chillspace

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.z3r08ug.chillspace.Screen.HomeScreen
import com.z3r08ug.chillspace.Screen.ProfileScreen

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object LoginScreen : Screen("login", R.string.login, Icons.Filled.Person)
    object RegisterScreen : Screen("register", R.string.register, Icons.Filled.Person)
    object HomeScreen : Screen("home", R.string.home, Icons.Filled.Home)
    object ProfileScreen : Screen("profile", R.string.profile, Icons.Filled.Person)
}

val screens = listOf(HomeScreen, ProfileScreen)