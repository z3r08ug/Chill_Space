package com.z3r0_8ug.ui_common.framework.ext

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

val Context.fragmentManager: FragmentManager?
  get() {
    return when (this) {
      is FragmentActivity -> supportFragmentManager
      else -> null
    }
  }