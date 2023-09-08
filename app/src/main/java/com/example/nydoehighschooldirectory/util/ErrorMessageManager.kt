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
    //I would have provided more specific error messages for various network issues.
    // For now, a generic message is displayed for network-related errors.
}