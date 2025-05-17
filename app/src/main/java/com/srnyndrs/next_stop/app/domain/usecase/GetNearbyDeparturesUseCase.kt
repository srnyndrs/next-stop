package com.srnyndrs.next_stop.app.domain.usecase

import com.srnyndrs.next_stop.shared.domain.model.single.PreferenceKey
import com.srnyndrs.next_stop.app.domain.usecase.location.GetUserLocationUseCase
import com.srnyndrs.next_stop.app.domain.usecase.user_preferences.GetIntPreferenceByKeyUseCase
import com.srnyndrs.next_stop.shared.domain.model.combined.NearbyDepartures
import com.srnyndrs.next_stop.shared.domain.repository.TransportRepository
import javax.inject.Inject

class GetNearbyDeparturesUseCase @Inject constructor(
    private val transportRepository: TransportRepository,
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val getIntPreferenceByKeyUseCase: GetIntPreferenceByKeyUseCase
) {
    suspend operator fun invoke(): Result<NearbyDepartures> {
        return getUserLocationUseCase().fold(
            onSuccess = { (lat, lon) ->
                getIntPreferenceByKeyUseCase(key = PreferenceKey.RADIUS).let { radius ->
                    getIntPreferenceByKeyUseCase(key = PreferenceKey.TIME). let { time ->
                        transportRepository.fetchDeparturesForLocation(lat, lon, radius, time)
                    }
                }
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }
}