package com.z3r08ug.chillspace.ui.home

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.z3r08ug.chillspace.Drawer
import com.z3r08ug.chillspace.Screen
import com.z3r08ug.chillspace.ui.theme.ChillSpaceTheme
import com.z3r08ug.chillspace.utils.*
import com.z3r0_8ug.ui_common.component.AppScaffold
import com.z3r0_8ug.ui_common.theme.AppTheme
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
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
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            PhotoPicker(user = user)
                        }
                    }
                }
            )
        }
    }
}

fun saveVideo(imageUri: Pair<Uri?, MediaType>, user: FirebaseUser?, context: Context) {
    var storageRef = user?.uid?.let { uid ->
        FirebaseStorage.getInstance().reference.child("videos").child(
            uid
        ).child(UUID.randomUUID().toString())
    }
    imageUri.first?.let { uri ->
        storageRef?.putFile(uri)?.addOnCompleteListener {
            if (it.isSuccessful) {
                Timber.d("Successfully uploaded video")
                Toast.makeText(context, "Video uploaded successfully", Toast.LENGTH_SHORT).show()
            } else if (!it.isSuccessful) {
                Timber.d("Error uploading video")
            }
        }
    }
}

fun savePhoto(imageUri: Pair<Uri?, MediaType>, user: FirebaseUser?, context: Context) {
    var storageRef = user?.uid?.let { uid ->
        FirebaseStorage.getInstance().reference.child("photos").child(
            uid
        ).child(UUID.randomUUID().toString())
    }
    imageUri.first?.let { uri ->
        storageRef?.putFile(uri)?.addOnCompleteListener {
            if (it.isSuccessful) {
                Timber.d("Successfully uploaded photo")
                Toast.makeText(context, "Photo uploaded successfully", Toast.LENGTH_SHORT).show()
            } else if (!it.isSuccessful) {
                Timber.d("Error uploading photo")
            }
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