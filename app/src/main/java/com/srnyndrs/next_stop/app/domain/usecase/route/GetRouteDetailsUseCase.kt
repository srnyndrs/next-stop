package com.srnyndrs.next_stop.app.domain.usecase.route

import com.srnyndrs.next_stop.app.domain.usecase.GetCurrentDateUseCase
import com.srnyndrs.next_stop.shared.domain.model.combined.RouteDetails
import com.srnyndrs.next_stop.shared.domain.repository.TransportRepository
import javax.inject.Inject

class GetRouteDetailsUseCase @Inject constructor(
    private val transportRepository: TransportRepository,
    private val getCurrentDateUseCase: GetCurrentDateUseCase
) {
    suspend operator fun invoke(routeId: String): Result<RouteDetails> {
        return getCurrentDateUseCase()?.let { date ->
            transportRepository.fetchRouteDetails(
                routeId = routeId,
                date = date
            )
        } ?: Result.failure(Exception()) // TODO
    }
}