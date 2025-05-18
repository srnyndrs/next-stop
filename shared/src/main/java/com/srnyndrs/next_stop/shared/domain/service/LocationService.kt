package com.srnyndrs.next_stop.shared.domain.service

import com.srnyndrs.next_stop.shared.domain.model.single.Location

interface LocationService {
    suspend fun requestLocation(): Location?
}