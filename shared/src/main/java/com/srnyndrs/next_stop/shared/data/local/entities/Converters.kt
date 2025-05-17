package com.srnyndrs.next_stop.shared.data.local.entities

import androidx.room.TypeConverter
import com.srnyndrs.next_stop.shared.data.remote.dto.ShapeType
import com.srnyndrs.next_stop.shared.domain.model.single.VehicleIcon

class ShapeTypeConverter {
    @TypeConverter
    fun toShapeType(value: String): ShapeType {
        return ShapeType.from(value)
    }

    @TypeConverter
    fun fromShapeType(value: ShapeType): String {
        return value.name
    }
}

class VehicleIconConverter {
    @TypeConverter
    fun toVehicleIcon(value: String): VehicleIcon {
        return VehicleIcon.from(value)
    }

    @TypeConverter
    fun fromVehicleIcon(value: VehicleIcon): String {
        return value.name
    }
}