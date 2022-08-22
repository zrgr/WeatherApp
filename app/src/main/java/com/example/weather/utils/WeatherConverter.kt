package com.example.weather.utils

import android.content.Context
import android.content.res.Resources
import android.provider.Settings.System.getString
import com.example.weather.R
import com.example.weather.models.api.Forecast
import com.example.weather.models.api.Period
import com.example.weather.models.api.Rep
import com.example.weather.models.app.Weather
import com.example.weather.models.app.WeatherForecast
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class WeatherConverter {

    fun convertForecast(forecast: Forecast): WeatherForecast {
        return WeatherForecast(
            locationName = formatLocation(forecast.SiteRep.DV.Location.name),
            dataDate = forecast.SiteRep.DV.dataDate,
            currentWeather = getThreeHourlyForecast(forecast.SiteRep.DV.Location.Period[0].Rep[0], getForecastTime(forecast.SiteRep.DV.Location.Period[0].Rep.size, 0)),
            futureWeather = getFutureForecast(forecast.SiteRep.DV.Location.Period)
        )
    }

    private fun jacketNeeded(chanceOfRain: Int): Boolean {
        return chanceOfRain > 30
    }

    private fun formatLocation(location: String): String {
        return location.lowercase().replaceFirstChar { it.uppercase() }
    }

    private fun getFutureForecast(period: List<Period>): List<Weather> {
        val periodToGet = 5
        val currentDayForecast = period[0].Rep.size - 1
        var nextDayForecast = 0
        var futureWeather = mutableListOf<Weather>()

        if(currentDayForecast < 5) {
            nextDayForecast = periodToGet - currentDayForecast
        }

        //Skip first forecast as it's already known
        for (i in 1..currentDayForecast) {
            futureWeather.add(getThreeHourlyForecast(period[0].Rep[i], getForecastTime(currentDayForecast, i)))
        }

        if (nextDayForecast != 0) {
            for (i in 0 until nextDayForecast) {
                futureWeather.add(getThreeHourlyForecast(period[1].Rep[i], getForecastTime(period[1].Rep.size, i)))
            }
        }

        return futureWeather
    }

    private fun getThreeHourlyForecast(forecast: Rep, time: String): Weather {
        return Weather(
            chanceOfRain = forecast.Pp,
            jacketNeeded = jacketNeeded(forecast.Pp.toInt()),
            windSpeed = forecast.S,
            temperature = forecast.T,
            weatherType = forecast.W,
            temperatureFeelsLike = forecast.F,
            time = time
        )
    }

    private fun getForecastTime(noOfForecast: Int, pos: Int): String {
        val times = listOf( 0, 3, 6, 9, 12, 15, 18, 21)
        var addPos: Int

        addPos = if (noOfForecast < times.size) {
            times.size - noOfForecast
        } else {
            pos
        }

        val currentHour = LocalDateTime.now().hour
        if (times[addPos] < currentHour && noOfForecast < 8)
            addPos += 1

        val timeCheck = LocalTime.of(times[addPos],0).toString()

        return LocalTime.of(times[addPos],0).toString()
    }
}