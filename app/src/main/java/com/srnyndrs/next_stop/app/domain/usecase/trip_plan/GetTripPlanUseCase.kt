package com.srnyndrs.next_stop.app.domain.usecase.trip_plan

import com.srnyndrs.next_stop.app.domain.usecase.location.GetUserLocationUseCase
import com.srnyndrs.next_stop.shared.data.remote.dto.RouteType
import com.srnyndrs.next_stop.shared.domain.model.combined.TripPlanResult
import com.srnyndrs.next_stop.shared.domain.repository.TransportRepository
import javax.inject.Inject

class GetTripPlanUseCase @Inject constructor(
    private val transportRepository: TransportRepository,
    private val getUserLocationUseCase: GetUserLocationUseCase
) {
    suspend operator fun invoke(
        destination: String,
        date: String,
        time: String,
        isArriveBy: Boolean,
        mode: List<RouteType>
    ): Result<TripPlanResult> {
        return try {
            getUserLocationUseCase().fold(
                onSuccess = { (lat, lon) ->
                    val source = "$lat,$lon"
                    transportRepository.fetchTripPlan(source, destination, date, time, isArriveBy, mode).fold(
                        onSuccess = { tripPlanResult ->
                            return Result.success(tripPlanResult)
                        },
                        onFailure = { throw it }
                    )
                },
                onFailure = { throw it }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}