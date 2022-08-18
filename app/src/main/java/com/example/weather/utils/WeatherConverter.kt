package com.example.weather.utils

import com.example.weather.models.api.Forecast
import com.example.weather.models.api.Period
import com.example.weather.models.api.Rep
import com.example.weather.models.app.Weather
import com.example.weather.models.app.WeatherForecast

class WeatherConverter {

    fun convertForecast(forecast: Forecast): WeatherForecast {
        return WeatherForecast(
            locationName = forecast.SiteRep.DV.Location.name,
            dataDate = forecast.SiteRep.DV.dataDate,
            currentWeather = getThreeHourlyForecast(forecast.SiteRep.DV.Location.Period[0].Rep[0]),
            futureWeather = emptyList()
        )
    }

    fun jacketNeeded(chanceOfRain: Int): String {
        return if (chanceOfRain > 30) "Yes" else "No"
    }

    fun <T> getFutureForcast(period: ArrayList<T>): List<Weather> {
        return listOf()
    }

    fun getThreeHourlyForecast(forecast: Rep): Weather {
        return Weather(
            chanceOfRain = forecast.Pp,
            jacketNeeded = jacketNeeded(forecast.Pp.toInt()),
            windSpeed = forecast.S,
            temperature = forecast.T,
            weatherType = forecast.W,
            temperatureFeelsLike = forecast.F
        )
    }
}