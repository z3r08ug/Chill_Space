package com.z3r08ug.chillspace.utils

import android.content.ClipData
import android.net.Uri
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseUser
import com.z3r08ug.chillspace.ui.home.saveVideo
import timber.log.Timber
import java.util.*


@Composable
fun UsePhotoPicker(user: FirebaseUser?) {
    var expanded by remember { mutableStateOf(false) }
    var typeOrdinal by remember { mutableStateOf(MediaType.VIDEO_ALL.ordinal) }
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
                        imageUri = Pair(resUri, resType)
                        saveVideo(imageUri as Pair<Uri?, MediaType>, user, context)
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
                        imageUri = Pair(resUri, resType)
                        saveVideo(imageUri as Pair<Uri?, MediaType>, user, context)
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
//        Image(
//            painter = rememberAsyncImagePainter(imageUri.first),
//            modifier = Modifier
//                .size(300.dp)
//                .align(Alignment.CenterHorizontally),
//            contentDescription = "selected Photo"
//        )
    }
}

