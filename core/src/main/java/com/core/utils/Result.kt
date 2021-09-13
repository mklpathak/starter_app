package com.core.utils

sealed class Result<out T>(
    val data: T? = null,
    val error: Throwable? = null
) {

    class Success<T>(data: T) : Result<T>(data)

    class Error<T>(error: Throwable?, data: T? = null) : Result<T>(data, error)

    class Loading<T> : Result<T>()
}