package com.z3r0_8ug.ui_common.framework.ext

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import com.z3r0_8ug.ui_common.component.SectionHeader

fun LazyListScope.spacer(modifier: Modifier) {
  item {
    Spacer(modifier = modifier)
  }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.sectionHeader(@StringRes header: Int) {
  stickyHeader {
    SectionHeader(header = header)
  }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.sectionHeader(header: String) {
  stickyHeader {
    SectionHeader(header = header)
  }
}