package com.srnyndrs.next_stop.shared.domain.model.single

import com.srnyndrs.next_stop.shared.data.remote.dto.ShapeType

data class Route(
    val routeId: String,
    val routeName: String,
    val textColor: String,
    val backgroundColor: String,
    val iconDisplayType: ShapeType,
    val routeType: VehicleIcon
)