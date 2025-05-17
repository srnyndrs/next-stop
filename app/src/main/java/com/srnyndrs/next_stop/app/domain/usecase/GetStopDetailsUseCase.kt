package com.srnyndrs.next_stop.app.domain.usecase

import com.srnyndrs.next_stop.shared.domain.model.combined.StopDetails
import com.srnyndrs.next_stop.shared.domain.repository.TransportRepository
import javax.inject.Inject

class GetStopDetailsUseCase @Inject constructor(
    private val transportRepository: TransportRepository
) {
    suspend operator fun invoke(stopId: String): Result<StopDetails> {
        return transportRepository.fetchDeparturesForStop(stopId)
    }
}