package com.example.weather.database

import android.content.Context
import androidx.room.Room
import com.example.weather.models.database.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*

private const val DATABASE_NAME = "location-database"

class LocationRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {

    private val database: LocationDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            LocationDatabase::class.java,
            DATABASE_NAME
        )
        .build()

    fun getLocations(): Flow<List<Location>> = database.locationDao().getLocations()
    suspend fun getCrime(id: UUID): Location = database.locationDao().getLocation(id)

    companion object {
        private var INSTANCE: LocationRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = LocationRepository(context)
            }
        }

        fun get(): LocationRepository {
            return INSTANCE ?:
            throw IllegalStateException("Location Repository must be initialized")
        }
    }
}