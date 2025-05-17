package com.srnyndrs.next_stop.shared.domain.model.single

data class Stop(
    val stopId: String,
    val stopName: String,
    val location: Location? = null,
    val direction: Float? = null,
    val colors: List<String> = emptyList(),
    val priority: Boolean = false
)
