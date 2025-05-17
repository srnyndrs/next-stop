package com.srnyndrs.next_stop.app.domain.usecase

import com.srnyndrs.next_stop.shared.domain.model.combined.StopSchedule
import com.srnyndrs.next_stop.shared.domain.repository.TransportRepository
import javax.inject.Inject

class GetStopScheduleUseCase @Inject constructor(
    private val transportRepository: TransportRepository
) {
    suspend operator fun invoke(stopId: String, date: String): Result<StopSchedule> {
        return transportRepository.fetchScheduleForStop(stopId, date)
    }
}