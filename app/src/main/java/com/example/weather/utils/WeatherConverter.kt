package com.example.weather.utils

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

        //Get position of most recent forecast
        var timeOfFirstForecast = getTimeOfFirstForeCast(forecast.SiteRep.DV.Location.Period[0].Rep)
        var positionToStart = 0

//        if (timeOfFirstForecast < getCurrentHour())
//            positionToStart = 1

        while (timeOfFirstForecast < getCurrentHour()) {
            positionToStart++
            timeOfFirstForecast += 3
        }

        return WeatherForecast(
            locationName = formatLocationName(forecast.SiteRep.DV.Location.name),
            dataDate = forecast.SiteRep.DV.dataDate,
            currentWeather = getThreeHourlyForecast(forecast.SiteRep.DV.Location.Period[0].Rep[positionToStart], getForecastTime(forecast.SiteRep.DV.Location.Period[0].Rep.size, positionToStart)),
            futureWeather = getFutureForecast(forecast.SiteRep.DV.Location.Period, positionToStart)
        )
    }

    private fun jacketNeeded(chanceOfRain: Int): Boolean {
        return chanceOfRain > 30
    }

    private fun formatLocationName(location: String): String {
        return location.lowercase().replaceFirstChar { it.uppercase() }
    }

    private fun getFutureForecast(period: List<Period>, positionToStart: Int): List<Weather> {
        val periodToGet = 5
        val currentDayForecast = period[0].Rep.size - 1
        var nextDayForecast = positionToStart
        var futureWeather = mutableListOf<Weather>()

        //Skip first forecast as it's already known
        for (i in positionToStart..currentDayForecast) {
            futureWeather.add(getThreeHourlyForecast(period[0].Rep[i], getForecastTime(i, i)))
        }

        //Need to get forecasts from next day if current day doesn't have enough
        if(currentDayForecast < periodToGet) {
            nextDayForecast = periodToGet - futureWeather.size
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

        var timeToGet: Int = if (noOfForecast < times.size) {
            times.size - noOfForecast
        } else {
            pos
        }

        val currentHour = LocalDateTime.now().hour
        if (times[timeToGet] < currentHour && noOfForecast < 8)
            timeToGet += 1

        return LocalTime.of(times[timeToGet],0).toString()
    }

    private fun getTimeOfFirstForeCast(currentDayForecast: List<Rep>): Int{
        val times = listOf( 0, 3, 6, 9, 12, 15, 18, 21)
        val noOfForecast = currentDayForecast.size

        var timeToGet: Int = if (currentDayForecast.size < times.size) {
            times.size - noOfForecast
        } else {
            0
        }

        return times[timeToGet];
    }

    private fun getCurrentHour() = LocalDateTime.now().hour

    private fun getForecastTimeStepStartPosition(): Int {
        return 0
    }
}