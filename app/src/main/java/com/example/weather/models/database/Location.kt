package com.example.weather.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location (
    @PrimaryKey
    val id: String,
    val elevation: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val region: String,
    @ColumnInfo(name = "unitary_auth_area")
    val unitaryAuthArea: String
)
