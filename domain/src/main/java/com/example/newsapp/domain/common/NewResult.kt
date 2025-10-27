package com.example.newsapp.domain.common

sealed class NewResult<out T> {
    data class Success<out T>(val data: T) : NewResult<T>()
    data class Error(val message: String) : NewResult<Nothing>()
    object Loading : NewResult<Nothing>()
}