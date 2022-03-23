package com.z3r0_8ug.ui_common.framework.ui.view

import com.google.android.material.datepicker.CalendarConstraints
import com.z3r0_8ug.ui_common.ext.toEpochMilli
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

object Calendar {
  /**
   * A [CalendarConstraints] that limits date selection to the past
   */
  fun pastDateCalendarConstraints(minDate: LocalDate = LocalDate.now().minusYears(100)): CalendarConstraints {
    val calendarStart = minDate.withDayOfMonth(1).toEpochMilli()
    val calendarEnd = LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1).toEpochMilli()

    val startDate = minDate.toEpochMilli()
    val endDate = LocalDate.now().toEpochMilli()

    return CalendarConstraints.Builder().apply {
      setStart(calendarStart)
      setEnd(calendarEnd)
      setValidator(RangeDateValidator(startDate, endDate))
    }.build()
  }

  /**
   * A [CalendarConstraints] implementation that limits the date input to
   * the past 130 years. 130 was chosen because the oldest documented human
   * lived to 122 years.
   */
  fun birthdateCalendarConstraints(): CalendarConstraints {
    return pastDateCalendarConstraints(LocalDate.now().minusYears(130))
  }

  @Parcelize
  private class RangeDateValidator(
    private val minDate: Long,
    private val maxDate: Long
  ): CalendarConstraints.DateValidator {
    override fun isValid(date: Long): Boolean {
      return date in (minDate + 1) until maxDate
    }
  }
}