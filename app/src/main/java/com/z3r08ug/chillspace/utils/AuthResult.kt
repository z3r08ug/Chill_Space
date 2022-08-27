package com.z3r08ug.chillspace.utils

import java.util.*

sealed class AuthResult {
    data class Success(val account: Account): AuthResult()
    data class Failure(val error: Error): AuthResult()

    enum class Error {
        UNKNOWN,
        INVALID_CREDENTIALS,
        USERNAME_UNAVAILABLE,
        INSUFFICIENT_PASSWORD_STRENGTH,
        NO_NETWORK_CONNECTION,
    }

    data class Account(
        val id: UUID,
        val householdId: UUID,
        val username: String?,
        val authenticated: Boolean,
    )
}
