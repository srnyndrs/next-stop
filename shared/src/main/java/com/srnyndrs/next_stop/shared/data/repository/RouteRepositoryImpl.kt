package com.srnyndrs.next_stop.shared.data.repository

import com.srnyndrs.next_stop.shared.data.local.dao.RouteDao
import com.srnyndrs.next_stop.shared.data.mapper.toRoute
import com.srnyndrs.next_stop.shared.data.mapper.toRouteEntity
import com.srnyndrs.next_stop.shared.domain.model.single.Route
import com.srnyndrs.next_stop.shared.domain.repository.RouteRepository
import javax.inject.Inject

class RouteRepositoryImpl @Inject constructor(
    private val routeDao: RouteDao
): RouteRepository {
    override fun getRoutes(): List<Route> {
        return routeDao.getRoutes().map { it.toRoute() }
    }

    override fun getRouteById(routeId: String): Route? {
        return routeDao.getRouteById(routeId = routeId)?.toRoute()
    }

    override fun insertRoute(route: Route) {
        routeDao.insertRoute(route = route.toRouteEntity())
    }

    override fun deleteRouteById(routeId: String) {
        routeDao.deleteById(routeId = routeId)
    }
}