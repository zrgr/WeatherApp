package com.example.weather.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import com.example.weather.FutureWeatherGridAdapter
import com.example.weather.FutureWeatherListener
import com.example.weather.R
import com.example.weather.WeatherViewModel
import com.example.weather.databinding.FragmentJacketBinding
import com.github.matteobattilana.weather.PrecipType


class JacketFragment : Fragment() {

    private val viewModel: WeatherViewModel by activityViewModels()
    lateinit var binding: FragmentJacketBinding
    lateinit var weather: PrecipType

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJacketBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.weather.observe(viewLifecycleOwner) { data ->
            val chanceOfRain = data.weatherToDisplay.chanceOfRain
            val currentWindSpeed = data.weatherToDisplay.windSpeed.toInt()
            val location = data.locationName
            binding.jacket.text = data.weatherToDisplay.jacketNeeded
            binding.currentChanceRain.text = getString(R.string.current_chance_rain, chanceOfRain, location)
            setWeather(chanceOfRain.toFloat(), currentWindSpeed)
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.futureWeather.adapter = FutureWeatherGridAdapter( FutureWeatherListener { weather ->
            viewModel.updateWeatherDisplay(weather)
        })

        return binding.root
    }

    private fun setWeather(chanceOfRain: Float, windSpeed: Int) {
        binding.wvWeatherView.apply {
            setWeatherData(PrecipType.RAIN)
            speed = 250
            emissionRate = chanceOfRain
            angle = windSpeed
            fadeOutPercent = 1f
            colour = Color.parseColor("#0214fa")
            scaleFactor = 3f
        }
    }
}