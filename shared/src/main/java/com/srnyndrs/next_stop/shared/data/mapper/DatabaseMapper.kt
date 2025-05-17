package com.srnyndrs.next_stop.shared.data.mapper

import com.srnyndrs.next_stop.shared.data.local.entities.RouteEntity
import com.srnyndrs.next_stop.shared.domain.model.single.Route

fun RouteEntity.toRoute() : Route {
    return Route(
        routeId = this.routeId,
        routeName = this.routeName,
        textColor = this.textColor,
        backgroundColor = this.backgroundColor,
        iconDisplayType = this.iconDisplayType,
        routeType = this.routeType
    )
}

fun Route.toRouteEntity(): RouteEntity {
    return RouteEntity(
        routeId = this.routeId,
        routeName = this.routeName,
        textColor = this.textColor,
        backgroundColor = this.backgroundColor,
        iconDisplayType = this.iconDisplayType,
        routeType = this.routeType
    )
}