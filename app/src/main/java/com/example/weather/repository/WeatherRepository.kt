package com.example.weather.repository

import com.example.weather.network.RetrofitInstance

class WeatherRepository {

    suspend fun getWeather(location: String) = RetrofitInstance.api.getWeather(location=location)
}