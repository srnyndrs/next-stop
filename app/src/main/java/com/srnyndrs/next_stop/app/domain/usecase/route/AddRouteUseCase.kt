package com.srnyndrs.next_stop.app.domain.usecase.route

import com.srnyndrs.next_stop.shared.domain.model.single.Route
import com.srnyndrs.next_stop.shared.domain.repository.RouteRepository
import javax.inject.Inject

class AddRouteUseCase @Inject constructor(
    private val routeRepository: RouteRepository
) {
    operator fun invoke(route: Route) {
        routeRepository.insertRoute(route)
    }
}