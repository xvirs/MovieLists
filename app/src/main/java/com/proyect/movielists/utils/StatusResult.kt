package com.proyect.movielists.utils

sealed class StatusResult<out T> {
    data class Success<T>(val value: T) : StatusResult<T>()
    data class Error(val message: String, val errorType: ErrorType? = null) : StatusResult<Nothing>()

    enum class ErrorType {
        NETWORK,
        TIMEOUT,
        UNKNOWN
    }
}