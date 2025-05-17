package com.srnyndrs.next_stop.shared.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.srnyndrs.next_stop.shared.data.local.entities.RouteEntity

@Dao
interface RouteDao {
    @Query("SELECT * from routes")
    fun getRoutes(): List<RouteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoute(route: RouteEntity)

    @Query("DELETE from routes WHERE routeId = :routeId")
    fun deleteById(routeId: String)

    @Query("SELECT * FROM routes where routeId = :routeId")
    fun getRouteById(routeId: String): RouteEntity?
}