package com.z3r08ug.chillspace.ui.home

import android.app.Activity
import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseUser
import com.z3r08ug.chillspace.R
import com.z3r08ug.chillspace.ui.theme.ChillSpaceTheme
import com.z3r08ug.chillspace.ui.util.Drawer
import com.z3r08ug.chillspace.ui.util.Screen
import com.z3r08ug.chillspace.utils.MainViewModel
import com.z3r08ug.chillspace.utils.PhotoPicker
import com.z3r0_8ug.ui_common.component.AppScaffold
import com.z3r0_8ug.ui_common.component.PostList
import com.z3r0_8ug.ui_common.component.Toolbar
import com.z3r0_8ug.ui_common.model.Photo
import com.z3r0_8ug.ui_common.model.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController?,
    mainViewModel: MainViewModel?,
    viewModel: HomeViewModel?,
    arguments: Bundle?,
    user: FirebaseUser?,
    activity: Activity?,
) {
    mainViewModel?.setCurrentScreen(Screen.HomeScreen)

    val coroutineScope = rememberCoroutineScope()
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

    val photos by lazy {
        viewModel?.photos
    }

    val userInfo by lazy {
        viewModel?.userInfo
    }

    ChillSpaceTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.DarkGray
        ) {
            AppScaffold(
                toolbar = {
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
                        Box(

                        ) {
                            Posts(viewModel, coroutineScope, user, photos, userInfo)
                            PhotoPicker(user = user)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun Posts(
    viewModel: HomeViewModel?,
    coroutineScope: CoroutineScope,
    user: FirebaseUser?,
    photos: MutableState<List<Photo>>?,
    userInfo: MutableState<UserInfo?>?
) {
    viewModel?.getPhotoList(coroutineScope, user)
    viewModel?.getUserInfo(coroutineScope, user)


    if (photos != null && userInfo != null) {
        userInfo.value?.let { PostList(photos.value, it) }
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
        activity = null
    )
}