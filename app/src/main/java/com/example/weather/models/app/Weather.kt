package com.example.weather.models.app

data class Weather(
    val jacketNeeded: Boolean,
    val chanceOfRain: String,
    val windSpeed: String,
    val temperature: String,
    val weatherType: String,
    val temperatureFeelsLike: String,
    val time: String
)
