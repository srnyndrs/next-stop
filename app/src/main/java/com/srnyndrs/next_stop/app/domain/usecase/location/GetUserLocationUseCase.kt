package com.srnyndrs.next_stop.app.domain.usecase.location

import com.srnyndrs.next_stop.app.domain.exceptions.LocationNotFound
import com.srnyndrs.next_stop.shared.domain.model.single.Location
import com.srnyndrs.next_stop.shared.domain.service.LocationService
import javax.inject.Inject

class GetUserLocationUseCase @Inject constructor(
    private val locationService: LocationService
) {
    suspend operator fun invoke(): Result<Location> {
        return locationService.requestLocation()?.let {
            Result.success(it)
        } ?: Result.failure(LocationNotFound())
    }
}