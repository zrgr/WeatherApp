package com.example.weather.models.api

data class Location(
    val Period: List<Period>,
    val continent: String,
    val country: String,
    val elevation: String,
    val i: String,
    val lat: String,
    val lon: String,
    val name: String
)