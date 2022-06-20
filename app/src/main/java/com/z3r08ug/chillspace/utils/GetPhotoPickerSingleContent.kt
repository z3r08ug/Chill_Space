package com.z3r08ug.chillspace.utils

import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import android.content.Intent
import android.provider.MediaStore
import android.app.Activity
import android.content.Context
import android.net.Uri

class GetPhotoPickerSingleContent : ActivityResultContract<SingleInputData, Uri?>() {
    @CallSuper
    override fun createIntent(context: Context, input: SingleInputData): Intent {
        val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
        intent.type = input.type
        return intent
    }

    override fun getSynchronousResult(
        context: Context,
        input: SingleInputData
    ): SynchronousResult<Uri?>? {
        return null
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return if (intent == null || resultCode != Activity.RESULT_OK) null else intent.data
    }
}