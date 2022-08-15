package com.example.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.models.Forecast
import com.example.weather.repository.WeatherRepository
import kotlinx.coroutines.launch

private const val TAG = "WeatherViewModel"

class WeatherViewModel : ViewModel(){

    private val _weather = MutableLiveData<Forecast>()
    val weather: LiveData<Forecast> = _weather

    private val _currentChanceOfRain = MutableLiveData<String>()
    val currentChanceOfRain: LiveData<String> = _currentChanceOfRain

    private val _repo = WeatherRepository()

    init {
        getWeatherForecast("324159", "3hourly")
    }

    fun getWeatherForecast(location: String, res: String) {
        viewModelScope.launch {
            try {
                val forecast = _repo.getWeather(location, res)
                _weather.value = _weather.value
                setValues(forecast)
            } catch (e: Exception) {
                Log.e(TAG, "getWeather() Api call failed");
            }
        }
    }

    private fun setValues(forecast: Forecast) {
        _currentChanceOfRain.value = forecast.SiteRep.DV.Location.Period[0].Rep[0].Pp
    }

}