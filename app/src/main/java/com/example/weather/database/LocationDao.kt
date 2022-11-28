package com.example.weather.database

import androidx.room.Dao
import androidx.room.Query
import com.example.weather.models.database.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * FROM location")
    fun getLocations(): Flow<List<Location>>

    @Query("SELECT * FROM location WHERE id=(:id)")
    suspend fun getLocation(id: String): Location
}