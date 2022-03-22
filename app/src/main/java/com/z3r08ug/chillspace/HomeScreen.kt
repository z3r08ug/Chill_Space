package com.z3r08ug.chillspace

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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseUser
import com.z3r08ug.chillspace.ui.theme.ChillSpaceTheme
import com.z3r08ug.chillspace.utils.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController?,
    arguments: Bundle?,
    user: FirebaseUser?,
    activity: Activity?,
    openDrawer: (() -> Unit)?,
    viewModel: MainViewModel?
) {
    viewModel?.setCurrentScreen(Screen.HomeScreen)

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
                        title = {navController
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
                                navController?.navigate(route) {
                                    launchSingleTop = true
                                }
                            })
                        },
                        drawerState = drawerState,
                        gesturesEnabled = drawerState.isOpen
                    ) {

                    }
                    ConstraintLayout {
                        
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun previewHomeScreen() {
    HomeScreen(navController = null, arguments = null, user = null, activity = null, openDrawer = { open() }, null)
}

fun open(): Unit {

}