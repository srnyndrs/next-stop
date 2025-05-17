package com.srnyndrs.next_stop.app.domain.usecase

import com.srnyndrs.next_stop.shared.domain.model.combined.TripDetails
import com.srnyndrs.next_stop.shared.domain.repository.TransportRepository
import javax.inject.Inject

class GetTripDetailsUseCase @Inject constructor(
    private val transportRepository: TransportRepository
) {
    suspend operator fun invoke(tripId: String): Result<TripDetails> {
        return transportRepository.fetchTripDetails(tripId)
    }
}