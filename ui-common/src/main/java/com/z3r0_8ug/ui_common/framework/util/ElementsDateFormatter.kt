package com.getelements.elements.ui.framework.util

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object ElementsDateFormatter {

    fun standard(locale: Locale):DateTimeFormatter = STANDARD
        .withLocale(locale)
        .withZone(ZoneId.systemDefault())

    val STANDARD:DateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    val SHORT:DateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy")
}