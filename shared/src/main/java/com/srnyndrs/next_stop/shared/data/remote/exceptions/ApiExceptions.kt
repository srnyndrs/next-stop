package com.srnyndrs.next_stop.shared.data.remote.exceptions

import com.srnyndrs.next_stop.shared.data.remote.dto.ResponseStatus

class ResponseException(status: ResponseStatus): Exception("An error occurred: ${status.name}")
class TripNotFoundException: Exception("Trip not found")
class RouteNotFoundException: Exception("Route not found")