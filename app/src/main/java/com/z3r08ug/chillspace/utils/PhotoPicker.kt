package com.z3r08ug.chillspace.utils

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.google.firebase.auth.FirebaseUser
import com.z3r08ug.chillspace.R
import com.z3r08ug.chillspace.ui.home.savePhoto
import com.z3r08ug.chillspace.ui.home.saveVideo
import com.z3r0_8ug.ui_common.component.menu.SpeedDialMenu
import com.z3r0_8ug.ui_common.component.menu.SpeedDialMenuItem
import com.z3r0_8ug.ui_common.theme.AppTheme
import timber.log.Timber

@Composable
fun PhotoPicker(user: FirebaseUser?) {
    val context = LocalContext.current
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
                    savePhoto(imageUri as Pair<Uri?, MediaType>, user, context)
                }
                MediaType.VIDEO_ALL, MediaType.VIDEO_WEBM -> {
                    Toast.makeText(context, "TODO impl...", Toast.LENGTH_SHORT).show()
                }
                MediaType.VIDEO_MP4 -> {
                    imageUri = Pair(resUri, resType)
                    saveVideo(imageUri as Pair<Uri?, MediaType>, user, context)
                }
                null -> {
//                    Toast.makeText(context, "handle error...", Toast.LENGTH_SHORT).show()
                }
            }
        }

    SpeedDialMenu(
        fabContent = {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Add Media"
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        SpeedDialMenuItem(
            text = "Upload Photo",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_add_photo_alternate_24),
                    contentDescription = null,
                    tint = AppTheme.colors.secondary
                )
            },
            onClick = {
                startForSingleModeResult.launch(
                    SingleInputData(
                        MediaType.findTypeNameByOrdinal(
                            MediaType.IMAGE_ALL.ordinal
                        )
                    )
                )
            }
        )
        SpeedDialMenuItem(
            text = "Upload Video",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_video_library_24),
                    contentDescription = null,
                    tint = AppTheme.colors.secondary
                )
            },
            onClick = {
                startForSingleModeResult.launch(
                    SingleInputData(
                        MediaType.findTypeNameByOrdinal(
                            MediaType.VIDEO_ALL.ordinal
                        )
                    )
                )
            }
        )
        SpeedDialMenuItem(
            text = "Capture Photo",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_camera_alt_24),
                    contentDescription = null,
                    tint = AppTheme.colors.secondary
                )
            },
            onClick = {
                //TODO Add camera feature
            }
        )
        SpeedDialMenuItem(
            text = "Capture Video",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_videocam_24),
                    contentDescription = null,
                    tint = AppTheme.colors.secondary
                )
            },
            onClick = {
                //TODO Add video capture feature
            }
        )
    }
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

internal fun handlePickerResponse(context: Context, uri: Uri?): Pair<Uri?, MediaType?> {
    Timber.d("handlePickerResponse: currentUri: $uri")
    val currentUri = uri ?: return Pair(null, null)
    val type = getMediaType(context, currentUri) ?: return Pair(null, null)// get media type
    return Pair(currentUri, type)
}