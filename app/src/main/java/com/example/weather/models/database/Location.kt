package com.example.weather.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName= "locations")
data class Location (
    @PrimaryKey
    @NotNull
    val id: String,
    val elevation: String?,
    val latitude: String?,
    val longitude: String?,
    val name: String?,
    val region: String?,
    @ColumnInfo(name = "unitary_auth_area")
    val unitaryAuthArea: String?,
    @ColumnInfo(name = "obs_source")
    val obsSource: String?,
    @ColumnInfo(name = "national_park")
    val nationalPark: String?
)
