package com.z3r08ug.chillspace.utils

import timber.log.Timber
import java.time.LocalDate

class DateUtils {
    companion object {
        /**
         * Date of Birth is in format yyyy-MM-dd
         */
        fun convertToLocalDate(dob: String): LocalDate? {
            return try {
                val formattedDate = "${dob[4]}${dob[5]}${dob[6]}${dob[7]}-${dob[0]}${dob[1]}-${dob[2]}${dob[3]}"
                LocalDate.parse(formattedDate)
            } catch (e: Exception) {
                Timber.e(e)
                null
            }
        }

        fun isUserUnder18(dob: LocalDate): Boolean {
            val year = LocalDate.now().year
            return year - dob.year <= 18
        }

        fun isValidDob(date: LocalDate?): Boolean {
            if (date != null) {
                val now = LocalDate.now()
                return if (date.year > now.year) {
                    false
                } else if (date.year == now.year) {
                    if (date.monthValue > now.monthValue) {
                        false
                    } else {
                        date.dayOfMonth <= now.dayOfMonth
                    }
                } else {
                    true
                }
            }
            return false
        }
    }
}