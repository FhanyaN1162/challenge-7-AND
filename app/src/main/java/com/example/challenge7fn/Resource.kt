package com.example.challenge7fn

sealed class Resource<out T> {
    data class Loading<out T>(val data: T? = null) : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val message: String, val data: T? = null) : Resource<T>()

    fun <R> map(transform: (T) -> R): Resource<R> {
        return when (this) {
            is Loading -> Loading(data?.let { transform(it) })
            is Success -> Success(transform(data))
            is Error -> Error(message, data?.let { transform(it) })
        }
    }
}
