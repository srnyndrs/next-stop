package com.srnyndrs.next_stop.shared.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.srnyndrs.next_stop.shared.data.local.dao.RouteDao
import com.srnyndrs.next_stop.shared.data.local.entities.RouteEntity
import com.srnyndrs.next_stop.shared.data.local.entities.ShapeTypeConverter
import com.srnyndrs.next_stop.shared.data.local.entities.VehicleIconConverter

@Database(entities = [RouteEntity::class], version = 1)
@TypeConverters(ShapeTypeConverter::class, VehicleIconConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun routeDao(): RouteDao
}