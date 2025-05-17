package com.srnyndrs.next_stop.shared.domain.repository

import com.srnyndrs.next_stop.shared.domain.model.single.Route

interface RouteRepository {
    fun getRoutes(): List<Route>
    fun getRouteById(routeId: String): Route?
    fun insertRoute(route: Route)
    fun deleteRouteById(routeId: String)
}