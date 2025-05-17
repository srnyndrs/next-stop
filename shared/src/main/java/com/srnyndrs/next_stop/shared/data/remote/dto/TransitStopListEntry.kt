package com.srnyndrs.next_stop.shared.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransitStopListEntry(
    val list: List<TransitStop>,
    val outOfRange: Boolean,
    val limitExceeded: Boolean,
    val references: TransitReferences,
    @SerialName("class")
    val clazz: String
)
