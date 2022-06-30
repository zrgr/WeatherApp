package com.example.weather.network

import com.example.weather.models.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("apod")
    suspend fun getApod(
        @Query("api_key")
        apiKey: String = API_KEY
    ): Weather
}