package com.example.weather.repository

import com.example.weather.network.RetrofitInstance

class WeatherRepository {

    suspend fun getApod() = RetrofitInstance.api.getApod()
}