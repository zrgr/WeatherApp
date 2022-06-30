package com.example.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.models.Weather
import com.example.weather.repository.WeatherRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class WeatherViewModel : ViewModel(){

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather> = _weather

    private val _repo = WeatherRepository()

    init {
        getWeather("Alloa")
    }

    fun getWeather(location: String) {
        viewModelScope.launch {
            try {
                _weather.value = _repo.getWeather(location)
            } catch (e: Exception) {
            }
        }
    }

}