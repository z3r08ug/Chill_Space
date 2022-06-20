package com.z3r08ug.chillspace.utils

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import timber.log.Timber

class GetPhotoPickerMultipleContent : ActivityResultContract<MultipleInputData, ClipData?>() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @CallSuper
    override fun createIntent(context: Context, input: MultipleInputData): Intent {
        val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
        input.maxNumPhotosAndVideos.let { maxNumPhotosAndVideos ->
            if (maxNumPhotosAndVideos < 2 || maxNumPhotosAndVideos > MediaStore.getPickImagesMaxLimit()) {
                Timber.w("getPickImagesMaxLimit: " + MediaStore.getPickImagesMaxLimit())
                Timber.w("The value of this intent-extra should be a positive integer greater than 1 and less than or equal to MediaStore#getPickImagesMaxLimit, otherwise Activity#RESULT_CANCELED is returned.")
            } else {
                intent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, maxNumPhotosAndVideos)
            }
        }
        return intent
    }

    override fun getSynchronousResult(
        context: Context,
        input: MultipleInputData
    ): SynchronousResult<ClipData?>? {
        return null
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ClipData? {
        return if (intent == null || resultCode != Activity.RESULT_OK) null else intent.clipData
    }
}