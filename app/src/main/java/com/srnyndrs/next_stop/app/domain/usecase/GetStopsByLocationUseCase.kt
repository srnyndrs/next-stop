package com.srnyndrs.next_stop.app.domain.usecase

import com.srnyndrs.next_stop.shared.domain.model.single.Location
import com.srnyndrs.next_stop.shared.domain.model.single.Stop
import com.srnyndrs.next_stop.shared.domain.repository.TransportRepository
import javax.inject.Inject

class GetStopsByLocationUseCase @Inject constructor(
    private val transportRepository: TransportRepository
) {
    suspend operator fun invoke(location: Location, locationSpan: Location): Result<List<Stop>> {
        return transportRepository.fetchStopsForLocation(location, locationSpan)
    }
}