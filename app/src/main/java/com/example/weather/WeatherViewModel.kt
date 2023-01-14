package com.example.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.database.LocationRepository
import com.example.weather.models.api.Forecast
import com.example.weather.models.app.Weather
import com.example.weather.models.app.WeatherForecast
import com.example.weather.models.database.Location
import com.example.weather.repository.WeatherRepository
import com.example.weather.utils.WeatherConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val TAG = "WeatherViewModel"

enum class WeatherApiStatus { LOADING, ERROR, DONE }

class WeatherViewModel : ViewModel() {

    private val locationRepository = LocationRepository.get()

    private val _weather = MutableLiveData<WeatherForecast>()
    val weather: LiveData<WeatherForecast> = _weather

    private val _currentChanceOfRain = MutableLiveData<String>()
    val currentChanceOfRain: LiveData<String> = _currentChanceOfRain

    private val _status = MutableLiveData<WeatherApiStatus>()
    val status: LiveData<WeatherApiStatus> = _status

    private val _repo = WeatherRepository()
    private val _converter = WeatherConverter()

//    private val _locations: MutableStateFlow<List<Location>> = MutableStateFlow(emptyList())
//    val locations: StateFlow<List<Location>>
//        get() = _locations.asStateFlow()

    var locations: List<Location> = emptyList()

    var location: Location? = null

    init {
        //getWeatherForecast("324159")
//        viewModelScope.launch {
//            locationRepository.getLocations().collect {
//                _locations.value = it
//            }
//        }

        viewModelScope.launch {
            try {
                location = getLocationFromDb()
            } catch(e: Exception) {
                Log.e(TAG, "error with room");
            }
        }
    }

    fun getWeatherForecast(location: String) {
        viewModelScope.launch {
            _status.value = WeatherApiStatus.LOADING
            try {
                val forecast = _repo.getWeather(location, "3hourly")
                _weather.value = _converter.convertForecast(forecast)
                setValues(forecast)
                _status.value = WeatherApiStatus.DONE
            } catch (e: Exception) {
                _status.value = WeatherApiStatus.ERROR
                Log.e(TAG, "getWeatherForecast() Api call failed");
            }
        }
    }

    fun getLocationFromDb(): Location? {
        //var location: Location? = null
        viewModelScope.launch {
            location = locationRepository.getLocation("3")
        }

        return location
    }

//    private suspend fun getLocations() {
//        CoroutineScope(Dispatchers.IO).launch {
//            locations = locationRepository.getLocations()
//        }
//    }
//
    fun getFirstLocation(): Location {
        var locations: List<Location> = emptyList()
        viewModelScope.launch {
            try {
                locations = locationRepository.getLocations()
            } catch(e: Exception) {
                Log.e(TAG, "error with room");
            }
        }

        return locations[0]
    }

    private fun setValues(forecast: Forecast) {
        _currentChanceOfRain.value = forecast.SiteRep.DV.Location.Period[0].Rep[0].Pp
    }

    fun updateWeatherDisplay(weather: Weather) {
        _weather.value?.weatherToDisplay = weather
        _weather.value = _weather.value
    }
}