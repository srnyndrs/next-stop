package com.srnyndrs.next_stop.shared.domain.model.combined

import com.srnyndrs.next_stop.shared.domain.model.single.Alert
import com.srnyndrs.next_stop.shared.domain.model.single.Route
import com.srnyndrs.next_stop.shared.domain.model.single.Stop

sealed class SearchResult(val name: String, val id: String) {
    data class RouteResult(val route: Route): SearchResult(name = route.routeName, id = route.routeId)
    data class StopResult(val stop: Stop): SearchResult(name = stop.stopName, id = stop.stopId)
    //data class AlertResult(val alert: Alert): SearchResult(name = alert.description)
}