package com.example.nydoehighschooldirectory.util

object ErrorMessageManager {

    private const val ERROR_NO_CONNECTION =
        "Unable to connect to the server. Please check your internet connection."
    fun getErrorMessage(exception: Throwable?): String {
        return when (exception) {
            is java.net.UnknownHostException -> ERROR_NO_CONNECTION
            else -> exception?.message ?: "An unknown error occurred."
        }
    }
}