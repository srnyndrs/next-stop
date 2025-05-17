package com.srnyndrs.next_stop.shared.domain.model.single

data class RouteVariant(
    val name: String,
    val headsign: String,
    val stopIds: List<String>,
    val routePoints: List<Location>? = null
)