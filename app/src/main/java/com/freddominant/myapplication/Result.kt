package com.freddominant.myapplication

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val error: Exception) : Result<Nothing>()
}
