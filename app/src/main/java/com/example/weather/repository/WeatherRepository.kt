package com.example.weather.repository

import com.example.weather.network.RetrofitInstance

class WeatherRepository {

    suspend fun getWeather(location: String, res: String) = RetrofitInstance.api.getForecast(location, res)
}