package org.example.tmdb.utils

sealed class Async<out R> {
    object Loading : Async<Nothing>()

    data class Success<out T>(val data: T) : Async<T>()

    data class Error(val message: String) : Async<Nothing>()
}