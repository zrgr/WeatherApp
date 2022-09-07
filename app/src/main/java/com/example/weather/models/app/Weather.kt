package com.example.weather.models.app

data class Weather(
    val jacketNeeded: String,
    var chanceOfRain: String,
    val windSpeed: String,
    val windGust: String,
    val temperature: String,
    val weatherType: String,
    val temperatureFeelsLike: String,
    val time: String,
    val weatherTypeImage: Int,
    val weatherTypeDescription: Int
)
