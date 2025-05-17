package com.srnyndrs.next_stop.shared.domain.service

import com.srnyndrs.next_stop.shared.domain.model.single.Location
import kotlinx.coroutines.flow.Flow

interface LocationService {
    fun requestLocationUpdates(): Flow<Location?>
    suspend fun requestLocation(): Location?
}