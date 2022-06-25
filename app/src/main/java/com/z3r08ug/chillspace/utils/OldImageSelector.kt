package com.z3r08ug.chillspace.utils

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


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
    ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    Column() {
        Button(onClick = {
            launcher.launch("video/*")
        }) {
            Text(text = "Pick Video")
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