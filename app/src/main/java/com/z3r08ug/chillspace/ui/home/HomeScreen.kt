package com.z3r08ug.chillspace.ui.home

import android.app.Activity
import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseUser
import com.z3r08ug.chillspace.Drawer
import com.z3r08ug.chillspace.Screen
import com.z3r08ug.chillspace.ui.theme.ChillSpaceTheme
import com.z3r08ug.chillspace.utils.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController?,
    mainViewModel: MainViewModel?,
    viewModel: HomeViewModel?,
    arguments: Bundle?,
    user: FirebaseUser?,
    activity: Activity?,
    openDrawer: (() -> Unit)?,
) {
    mainViewModel?.setCurrentScreen(Screen.HomeScreen)

    val scaffoldState = rememberScaffoldState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDrawer = {
        scope.launch {
            drawerState.open()
        }
    }
    val closeDrawer = {
        scope.launch {
            drawerState.close()
        }
    }

    ChillSpaceTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.DarkGray
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        backgroundColor = MaterialTheme.colors.secondary,
                        title = {
                            Text(text = "Home")
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                if (drawerState.isOpen) {
                                    closeDrawer()
                                } else {
                                    openDrawer()
                                }
                            }) {
                                Icon(Icons.Filled.Menu, contentDescription = "")
                            }
                        })
                },
                content = {
                    ModalDrawer(
                        drawerContent = {
                            Drawer(onDestinationClicked = { route ->
                                scope.launch {
                                    drawerState.close()
                                }
                                if (route != Screen.NewLoginScreen.route) {
                                    navController?.navigate(route) {
                                        launchSingleTop = true
                                    }
                                } else {
                                    navController?.navigate(route)
                                }
                            })
                        },
                        drawerState = drawerState,
                        gesturesEnabled = drawerState.isOpen
                    ) {

                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun previewHomeScreen() {
    HomeScreen(
        navController = null,
        mainViewModel = null,
        viewModel = null,
        arguments = null,
        user = null,
        activity = null,
        openDrawer = {  }
    )
}