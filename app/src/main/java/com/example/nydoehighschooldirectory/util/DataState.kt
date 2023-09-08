package com.example.nydoehighschooldirectory.util

sealed class DataState<out R> {

    // Represents a successful data state with associated data of type T.
    data class Success<out T>(val data: T) : DataState<T>()
    // Represents an error data state with an associated exception.
    data class Error(val exception: Exception) : DataState<Nothing>()
    // Represents an empty data state when no data is available.
    object Empty: DataState<Nothing>()
    // Represents a loading data state when data is being fetched.
    object Loading : DataState<Nothing>()

}
