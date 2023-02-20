package com.example.weather.network

import com.example.weather.BuildConfig
import com.example.weather.models.api.Forecast
import com.example.weather.utils.Constants.Companion.FORECAST
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("{location}")
    suspend fun getForecast(
        @Path("location")
        location: String,
        @Query("res")
        res: String = FORECAST,
        @Query("key")
        key: String = BuildConfig.API_KEY
    ): Forecast
}