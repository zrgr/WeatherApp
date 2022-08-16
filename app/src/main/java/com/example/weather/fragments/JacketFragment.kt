package com.example.weather.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
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

        viewModel.getWeatherForecast("324159", "3hourly")
        binding.currentChanceRain.text = getString(R.string.current_chance_rain, viewModel.currentChanceOfRain.value.toString())

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setWeather()

        return binding.root
    }

    private fun setWeather() {

        binding.wvWeatherView.apply {
            setWeatherData(PrecipType.RAIN)
            speed = 250
            emissionRate = 100f
            angle = 0
            fadeOutPercent = 1f
            colour = Color.parseColor("#0214fa")
            scaleFactor = 2f

        }
    }

}