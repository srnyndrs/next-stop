package com.srnyndrs.next_stop.app.presentation.common

sealed class UiState<T>(val data:  T? = null, val message : String? = null) {
    class Loading<T>(data: T?= null) : UiState<T>(data)
    class Success<T> (data: T) : UiState<T>(data)
    class Error<T>(data: T? = null, message: String) : UiState<T>(data, message)
    class Empty<T>: UiState<T>()
}