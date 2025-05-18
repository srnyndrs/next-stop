package com.srnyndrs.next_stop.shared.domain.model.combined

import com.srnyndrs.next_stop.shared.domain.model.single.Route
import com.srnyndrs.next_stop.shared.domain.model.single.RouteVariant
import com.srnyndrs.next_stop.shared.domain.model.single.Stop

data class RouteDetails(
    val route: Route,
    val description: String? = null,
    val variants: List<RouteVariant>,
    val stops: Map<String, Stop>
)
