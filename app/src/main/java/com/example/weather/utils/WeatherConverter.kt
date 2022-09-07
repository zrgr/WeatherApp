package com.example.weather.utils

import com.example.weather.R
import com.example.weather.models.api.Forecast
import com.example.weather.models.api.Period
import com.example.weather.models.api.Rep
import com.example.weather.models.app.Weather
import com.example.weather.models.app.WeatherForecast
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class WeatherConverter {

    fun convertForecast(forecast: Forecast): WeatherForecast {

        //Get position of most recent forecast
        val positionToStart = getForecastTimeStepStartPosition(forecast.SiteRep.DV.Location.Period[0].Rep)

        val initialWeather = getThreeHourlyForecast(forecast.SiteRep.DV.Location.Period[0].Rep[positionToStart], getForecastTime(forecast.SiteRep.DV.Location.Period[0].Rep.size, positionToStart))

        return WeatherForecast(
            locationName = formatLocationName(forecast.SiteRep.DV.Location.name),
            dataDate = forecast.SiteRep.DV.dataDate,
            currentWeather = initialWeather,
            futureWeather = getFutureForecast(forecast.SiteRep.DV.Location.Period, positionToStart + 1),
            weatherToDisplay = initialWeather
        )
    }

    private fun jacketNeeded(chanceOfRain: Int): String {
        return if (chanceOfRain > 30) "Yes" else "No"
    }

    private fun formatLocationName(location: String): String {
        return location.lowercase().replaceFirstChar { it.uppercase() }
    }

    private fun getFutureForecast(period: List<Period>, positionToStart: Int): List<Weather> {
        val periodToGet = 5
        //TODO: refactor name
        val currentDayForecast = period[0].Rep.size
        var nextDayForecast = positionToStart
        var futureWeather = mutableListOf<Weather>()

        //Skip first forecast as it's already known
        if(positionToStart < currentDayForecast) {
            for (i in positionToStart until currentDayForecast) {
                futureWeather.add(getThreeHourlyForecast(period[0].Rep[i], getForecastTime(i, i)))
            }
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
            windGust = forecast.G,
            temperature = forecast.T,
            weatherType = forecast.W,
            temperatureFeelsLike = forecast.F,
            time = time,
            weatherTypeImage = getImage(forecast.W.toInt()),
            weatherTypeDescription = getWeatherDescription(forecast.W.toInt())
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

    private fun getForecastTimeStepStartPosition(forecasts: List<Rep>): Int {
        var timeOfFirstForecast = getTimeOfFirstForeCast(forecasts)
        var positionToStart = 0

        while (timeOfFirstForecast < getCurrentHour() && (timeOfFirstForecast + 3) < 21) {
            positionToStart++
            timeOfFirstForecast += 3
        }

        return positionToStart
    }

    private fun getImage(weatherType: Int): Int {
        return when (weatherType) {
            0 -> R.drawable.weather_type_clear_night
            1 -> R.drawable.weather_type_sunny_day
            2 -> R.drawable.weather_type_partly_cloudy_day
            3 -> R.drawable.weather_type_partly_cloudy_day
            5, 6, 7, 8 -> R.drawable.weather_type_assorted
            9 -> R.drawable.weather_type_light_rain_shower_night
            10, 11 -> R.drawable.weather_type_light_rain_day
            12 -> R.drawable.weather_type_light_rain
            13 -> R.drawable.weather_type_heavy_rain_night
            14 -> R.drawable.weather_type_heavy_rain_day
            15 -> R.drawable.weather_type_heavy_rain_day
            16 -> R.drawable.weather_type_sleet_shower_night
            17, 18 -> R.drawable.weather_type_sleet_shower_day
            19 -> R.drawable.weather_type_hail_shower_night
            20, 21 -> R.drawable.weather_type_hail_shower_day
            23 -> R.drawable.weather_type_light_snow_night
            24 -> R.drawable.weather_type_light_snow_day
            25 -> R.drawable.weather_type_heavy_snow_night
            26 -> R.drawable.weather_type_heavy_snow_day
            27 -> R.drawable.weather_type_heavy_snow_day
            28 -> R.drawable.weather_type_thunder_night
            29 -> R.drawable.weather_type_thunder_day
            30 -> R.drawable.weather_type_thunder
            else -> R.drawable.weather_type_partly_cloudy_day
        }
    }

    private fun getWeatherDescription(weatherType: Int): Int {
        return when (weatherType) {
            0 -> R.string.weather_type_clear_night
            1 -> R.string.weather_type_clear_night
            2, 3 -> R.string.weather_type_partly_cloudy
            5 -> R.string.weather_type_mist
            6 -> R.string.weather_type_fog
            7 -> R.string.weather_type_cloudy
            8 -> R.string.weather_type_overcast
            9, 10 -> R.string.weather_type_light_rain_shower
            11 -> R.string.weather_type_drizzle
            12 -> R.string.weather_type_light_rain
            13, 14 -> R.string.weather_type_heavy_rain_shower
            15 -> R.string.weather_type_heavy_rain
            16, 17 -> R.string.weather_type_sleet_shower
            18 -> R.string.weather_type_sleet
            19, 20 -> R.string.weather_type_hail_shower
            21 -> R.string.weather_type_hail
            22, 23 -> R.string.weather_type_light_snow_shower
            24 -> R.string.weather_type_light_snow
            25, 26 -> R.string.weather_type_heavy_snow_shower
            27 -> R.string.weather_type_heavy_snow
            28, 29 -> R.string.weather_type_thunder_shower
            30 -> R.string.weather_type_thunder
            else -> R.string.weather_type_not_available
        }
    }
}