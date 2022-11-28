package com.example.weather

import android.app.Application
import com.example.weather.database.LocationRepository

class WeatherApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        LocationRepository.initialize(this)
    }
}