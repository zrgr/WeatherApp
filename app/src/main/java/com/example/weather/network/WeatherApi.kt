package com.example.weather.network

import com.example.weather.BuildConfig
import com.example.weather.models.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current.json")
    suspend fun getWeather(
        @Query("key")
        key: String = BuildConfig.API_KEY,
        @Query("key")
        location: String
    ): Weather
}