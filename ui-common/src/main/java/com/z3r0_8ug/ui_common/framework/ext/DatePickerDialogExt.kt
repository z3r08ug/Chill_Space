
package com.z3r0_8ug.ui_common.framework.ext

import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.z3r0_8ug.ui_common.ext.toEpochMilli
import timber.log.Timber
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

fun showDatePickerDialog(
  fragmentManager: FragmentManager?,
  title: String,
  startDate: LocalDate?,
  calendarConstraints: CalendarConstraints? = null,
  onDateSelected: (selectedDate: LocalDate) -> Unit
) {
  if (fragmentManager == null) {
    Timber.e("Unable to show DatePickerDialog due to a null FragmentManager")
    return
  }

  MaterialDatePicker.Builder.datePicker().apply {
    setTitleText(title)
    calendarConstraints?.let {
      setCalendarConstraints(it)
    }
    startDate?.let {
      setSelection(it.toEpochMilli())
    }
  }.build().apply {
    addOnPositiveButtonClickListener {
      val instant = Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC)
      onDateSelected(LocalDate.from(instant))
    }
  }.show(fragmentManager, null)
}