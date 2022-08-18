package com.example.weather.models.app

data class WeatherForecast(
    val locationName: String,
    val dataDate: String,
    val currentWeather: Weather,
    val futureWeather: List<Weather>
)