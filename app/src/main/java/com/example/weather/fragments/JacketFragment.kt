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
            val chanceOfRain = data.SiteRep.DV.Location.Period[0].Rep[0].Pp
            if (chanceOfRain.toInt() > 50) {
                binding.jacket.text = "Yes"
            } else {
                binding.jacket.text = "No"
            }
            binding.currentChanceRain.text = getString(R.string.current_chance_rain, chanceOfRain)
            setWeather(chanceOfRain.toFloat())
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)



        return binding.root
    }

    private fun setWeather(chanceOfRain: Float) {
        binding.wvWeatherView.apply {
            setWeatherData(PrecipType.RAIN)
            speed = 250
            emissionRate = chanceOfRain
            angle = 0
            fadeOutPercent = 1f
            colour = Color.parseColor("#0214fa")
            scaleFactor = 2f
        }
    }
}