package com.example.nydoehighschooldirectory.util

sealed class DataState<out R> {

    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    object Empty: DataState<Nothing>()
    object Loading : DataState<Nothing>()

}
