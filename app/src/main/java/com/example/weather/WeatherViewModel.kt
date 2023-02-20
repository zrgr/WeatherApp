package com.example.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.weather.models.api.Forecast
import com.example.weather.models.app.Weather
import com.example.weather.models.app.WeatherForecast
import com.example.weather.repository.WeatherRepository
import com.example.weather.utils.WeatherConverter
import kotlinx.coroutines.launch

private const val TAG = "WeatherViewModel"

enum class WeatherApiStatus { LOADING, ERROR, DONE }

class WeatherViewModel : ViewModel(){

    private val _weather = MutableLiveData<WeatherForecast>()
    val weather: LiveData<WeatherForecast> = _weather

    private val _currentChanceOfRain = MutableLiveData<String>()
    val currentChanceOfRain: LiveData<String> = _currentChanceOfRain

    private val _status = MutableLiveData<WeatherApiStatus>()
    val status: LiveData<WeatherApiStatus> = _status

    private val _repo = WeatherRepository()
    private val _converter = WeatherConverter()

    fun getWeatherForecast(location: String, jacketNeeded: Int) {
        viewModelScope.launch {
            _status.value = WeatherApiStatus.LOADING
            try {
                val forecast = _repo.getWeather(location)
                _weather.value = _converter.convertForecast(forecast, jacketNeeded)
                setValues(forecast)
                _status.value = WeatherApiStatus.DONE
            } catch (e: Exception) {
                _status.value = WeatherApiStatus.ERROR
                Log.e(TAG, "getWeatherForecast() Api call failed");
            }
        }
    }

    private fun setValues(forecast: Forecast) {
        _currentChanceOfRain.value = forecast.SiteRep.DV.Location.Period[0].Rep[0].Pp
    }

    fun updateWeatherDisplay(weather: Weather) {
        _weather.value?.weatherToDisplay = weather
        _weather.value = _weather.value
    }
}