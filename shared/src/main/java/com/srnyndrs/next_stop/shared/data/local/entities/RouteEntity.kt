package com.srnyndrs.next_stop.shared.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.srnyndrs.next_stop.shared.data.remote.dto.ShapeType
import com.srnyndrs.next_stop.shared.domain.model.single.VehicleIcon

@Entity(tableName = "routes")
data class RouteEntity(
    @PrimaryKey
    val routeId: String,
    val routeName: String,
    val textColor: String,
    val backgroundColor: String,
    val iconDisplayType: ShapeType,
    val routeType: VehicleIcon
)