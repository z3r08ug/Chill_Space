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
import android.util.Log
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseUser
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
//                            RequestContentPermission()
//                            UsePhotoPicker()
                            AddNewPost()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun AddNewPost() {
    FloatingActionButton(
        onClick = {  },
        backgroundColor = AppTheme.colors.secondary
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Add Media"
        )
    }
}

@Composable
fun UsePhotoPicker() {
    var expanded by remember { mutableStateOf(false) }
    var typeOrdinal by remember { mutableStateOf(MediaType.IMAGE_ALL.ordinal) }
    val radioOptions = listOf("single", "multiple")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    var maxNum by remember { mutableStateOf("10") }
    val context = LocalContext.current
    val scrollState: ScrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                PaddingValues(20.dp)
            )
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Type(Single Mode Only)")
        Box {
            OutlinedTextField(
                value = MediaType.findTypeNameByOrdinal(typeOrdinal),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledTextColor = LocalContentColor.current.copy(
                        LocalContentAlpha.current
                    )
                ),
                onValueChange = { },
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                placeholder = { Text(text = "select Media Type") }
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                val menuItems = MediaType.values()
                menuItems.forEach {
                    DropdownMenuItem(
                        onClick = {
                            typeOrdinal = it.ordinal
                            expanded = false
                        },
                        content = {
                            Text(MediaType.findTypeNameByOrdinal(it.ordinal))
                            Icon(
                                Icons.Rounded.Person,
                                contentDescription = "Media type: ${it.name}"
                            )
                        }
                    )
                }
            }
        }

        Text(text = "Select Mode")

        // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
        Column(Modifier.selectableGroup()) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
        OutlinedTextField(
            value = maxNum,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { maxNum = it },
            modifier = Modifier.fillMaxWidth(),
            isError = TextUtils.isEmpty(maxNum) || !TextUtils.isDigitsOnly(maxNum),
            label = {
                Text(
                    text = "max Num Photos And Videos(Multiple Mode Only)", fontSize = 12.sp
                )
            }
        )

        var imageUri by remember { mutableStateOf<Pair<Uri?, MediaType?>>(Pair(null, null)) }
        val startForSingleModeResult =
            rememberLauncherForActivityResult(GetPhotoPickerSingleContent()) { uri: Uri? ->
                // Get photo picker response for single select.
                Timber.d("GetPhotoPickerSingleContent: uri: $uri")
                // Do stuff with the photo/video URI.
                val (resUri, resType) = handlePickerResponse(context, uri)
                when (resType) {
                    MediaType.IMAGE_ALL, MediaType.IMAGE_GIF -> {
                        Toast.makeText(context, "TODO impl...", Toast.LENGTH_SHORT).show()
                    }
                    MediaType.IMAGE_PNG, MediaType.IMAGE_JPG -> {
                        imageUri = Pair(resUri, resType)
                    }
                    MediaType.VIDEO_ALL, MediaType.VIDEO_WEBM -> {
                        Toast.makeText(context, "TODO impl...", Toast.LENGTH_SHORT).show()
                    }
                    MediaType.VIDEO_MP4 -> {
//                        MainActivity.startVideoActivity(context, resUri)
                    }
                    null -> {
                        Toast.makeText(context, "handle error...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        val startForMultipleModeResult =
            rememberLauncherForActivityResult(GetPhotoPickerMultipleContent()) { data: ClipData? ->
                Timber.d("GetPhotoPickerMultipleContent: clipData: $data")
                val clipData = data ?: return@rememberLauncherForActivityResult
                // output log.
                (0 until clipData.itemCount).forEach { i ->
                    val currentUri2 = clipData.getItemAt(i).uri
                    Timber.d("onActivityResult: currentUri$currentUri2")
                    // Do stuff with each photo/video URI.
                }
                // Get photo picker response for multi select.
                val itemCnt = clipData.itemCount
                val bound = if (itemCnt == 1) itemCnt else itemCnt - 1
                val randomIndex = Random().nextInt(bound)
                Timber.d("onActivityResult: itemCnt: $itemCnt")
                Timber.d("onActivityResult: randomIndex: $randomIndex")
                val randomUri = clipData.getItemAt(randomIndex).uri
                val (resUri, resType) = handlePickerResponse(context, randomUri)
                when (resType) {
                    MediaType.IMAGE_ALL, MediaType.IMAGE_GIF -> {
                        Toast.makeText(context, "TODO impl...", Toast.LENGTH_SHORT).show()
                    }
                    MediaType.IMAGE_PNG, MediaType.IMAGE_JPG -> {
                        imageUri = Pair(resUri, resType)
                    }
                    MediaType.VIDEO_ALL, MediaType.VIDEO_WEBM -> {
                        Toast.makeText(context, "TODO impl...", Toast.LENGTH_SHORT).show()
                    }
                    MediaType.VIDEO_MP4 -> {
//                        MainActivity.startVideoActivity(context, resUri)
                    }
                    null -> {
                        Toast.makeText(context, "handle error...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        OutlinedButton(
            onClick = {
                Timber.d("Button Clicked")
                if (selectedOption == radioOptions[0]) {
                    // single mode
                    startForSingleModeResult.launch(
                        SingleInputData(
                            MediaType.findTypeNameByOrdinal(
                                typeOrdinal
                            )
                        )
                    )
                } else if (selectedOption == radioOptions[1]) {
                    startForMultipleModeResult.launch(MultipleInputData(maxNum.toInt()))
                }
            },
            enabled = !TextUtils.isEmpty(maxNum) && TextUtils.isDigitsOnly(maxNum),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "launch photo Picker")
        }
        Image(
            painter = rememberImagePainter(imageUri.first),
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.CenterHorizontally),
            contentDescription = "selected Photo"
        )
    }
}

internal fun handlePickerResponse(context: Context, uri: Uri?): Pair<Uri?, MediaType?> {
    Timber.d("handlePickerResponse: currentUri: $uri")
    val currentUri = uri ?: return Pair(null, null)
    val type = getMediaType(context, currentUri) ?: return Pair(null, null)// get media type
    return Pair(currentUri, type)
}

private fun getMediaType(context: Context, currentUri: Uri): MediaType? {
    val type = context.contentResolver.getType(currentUri) ?: return null
    val mimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(type)
    Timber.d("type: $type mimeType: $mimeType") // type: video/mp4 mimeType: mp4
    var result: MediaType? = null
    try {
        result = MediaType.find(type)
    } catch (ex: IllegalArgumentException) {
        Toast.makeText(context, ex.localizedMessage, Toast.LENGTH_SHORT).show()
    }
    result?.let { ret -> Timber.d("getMimeType: ${ret.type}") }// video/mp4
    return result
}

@Composable
fun RequestContentPermission() {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract =
    GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    Column() {
        Button(onClick = {
            launcher.launch("image/*")
        }) {
            Text(text = "Pick image")
        }

        Spacer(modifier = Modifier.height(12.dp))

        imageUri.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver,it)

            } else {
                val source = it?.let { it1 ->
                    ImageDecoder
                        .createSource(context.contentResolver, it1)
                }
                bitmap.value = source?.let { it1 -> ImageDecoder.decodeBitmap(it1) }
            }

//            bitmap.value?.let {  btm ->
//                Image(bitmap = btm.asImageBitmap(),
//                    contentDescription =null,
//                    modifier = Modifier.size(400.dp))
//            }
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