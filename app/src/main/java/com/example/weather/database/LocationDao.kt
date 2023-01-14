package com.example.weather.database

import androidx.room.Dao
import androidx.room.Query
import com.example.weather.models.database.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * FROM locations")
    suspend fun getLocations(): List<Location>

    @Query("SELECT * FROM locations WHERE id=:id")
    suspend fun getLocation(id: String): Location
}