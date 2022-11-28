package com.example.weather.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather.models.database.Location


@Database(entities = [Location::class], version=1)
abstract class LocationDatabase: RoomDatabase() {

    abstract fun locationDao(): LocationDao
}