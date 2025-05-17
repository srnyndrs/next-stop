package com.srnyndrs.next_stop.shared.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val currentTime: Long,
    val version: Int,
    val status: ResponseStatus,
    val code: Int,
    val text: String,
    val data: T
)