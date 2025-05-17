package com.srnyndrs.next_stop.app.domain.usecase.route

import com.srnyndrs.next_stop.shared.domain.repository.RouteRepository
import javax.inject.Inject

class DeleteRouteUseCase @Inject constructor(
    private val routeRepository: RouteRepository
) {
    operator fun invoke(routeId: String) {
        routeRepository.deleteRouteById(routeId)
    }
}