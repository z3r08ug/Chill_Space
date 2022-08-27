package com.z3r0_8ug.ui_common.model

import java.time.LocalDate

data class UserInfo(
    val dob: LocalDate?,
    val email: String?,
    val uid: String?,
    val under_18: Boolean?,
    val userName: String?
)
