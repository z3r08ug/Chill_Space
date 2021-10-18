package com.z3r08ug.chillspace

import androidx.annotation.StringRes

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object LoginScreen : Screen("login", R.string.login)
    object RegisterScreen : Screen("register", R.string.register)
    object HomeScreen : Screen("home", R.string.home)
}