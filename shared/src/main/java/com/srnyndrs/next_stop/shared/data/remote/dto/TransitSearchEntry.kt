package com.srnyndrs.next_stop.shared.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransitSearchEntry(
    val limitExceeded: Boolean,
    val entry: TransitSearch,
    val references: TransitReferences,
    @SerialName("class")
    val clazz: String
)

@Serializable
data class TransitSearch(
    val query: String,
    val stopIds: List<String>? = null,
    val routeIds: List<String>? = null,
    val alertIds: List<String>? = null
)
