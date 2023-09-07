package com.example.nydoehighschooldirectory.util

import retrofit2.HttpException
import java.io.IOException

object ErrorMessageManager {

    const val ERROR_BAD_REQUEST = 400
    const val ERROR_NOT_FOUND = 404
    const val ERROR_NO_CONNECTION = 503
    const val ERROR_GENERAL = -1
    private val errorMessages = mapOf(

        ERROR_BAD_REQUEST to "Bad Request: The request made was invalid.",
        ERROR_NOT_FOUND to "Not Found: The requested resource was not found.",
        ERROR_NO_CONNECTION to "No Connection: Please check your internet connection."

    )
    fun getErrorMessage(errorCode: Int): String {
        return errorMessages[errorCode] ?: "An unknown error occurred."
    }

    fun getErrorMessage(exception: Throwable?): String {
        return when (exception) {
            is IOException -> getErrorMessage(ERROR_NO_CONNECTION)
            is HttpException -> {
                when (exception.code()) {
                    ERROR_BAD_REQUEST -> getErrorMessage(ERROR_BAD_REQUEST)
                    ERROR_NOT_FOUND -> getErrorMessage(ERROR_NOT_FOUND)
                    else -> getErrorMessage(ERROR_GENERAL)
                }
            }
            else -> getErrorMessage(ERROR_GENERAL)
        }
    }

}