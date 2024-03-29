package com.example.weather.fragments

//import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.weather.FutureWeatherGridAdapter
import com.example.weather.FutureWeatherListener
import com.example.weather.R
import com.example.weather.WeatherViewModel
import com.example.weather.databinding.FragmentJacketBinding
import com.example.weather.models.app.WeatherForecast
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

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val sp = PreferenceManager.getDefaultSharedPreferences(requireActivity())

        val jacketNeeded = sp.getInt("jacket_needed", 30)

        viewModel.getWeatherForecast("324159", jacketNeeded)

        viewModel.weather.observe(viewLifecycleOwner) { data ->

            val currentWindSpeed = data.weatherToDisplay.windSpeed.toInt()

            binding.apply {
                jacket.text = data.weatherToDisplay.jacketNeeded
                weatherType.setImageResource(data.weatherToDisplay.weatherTypeImage)
                temperature.text = getString(R.string.temperature, data.weatherToDisplay.temperature)
                temperatureFeelsLike.text = getString(R.string.temperature_feels_like, data.weatherToDisplay.temperatureFeelsLike)
                windSpeed.text = getString(R.string.wind_speed, data.weatherToDisplay.windSpeed)
                weatherTypeDescription.text = getString(data.weatherToDisplay.weatherTypeDescription)
            }

            createChanceOfRainText(data)
            setWeather(data.weatherToDisplay.chanceOfRain.toFloat(), currentWindSpeed)
        }

        binding.futureWeather.adapter = FutureWeatherGridAdapter( FutureWeatherListener { weather ->
            viewModel.updateWeatherDisplay(weather)
        })

        binding.fabSettings.setOnClickListener {
            findNavController().navigate(R.id.action_jacketFragment_to_settingsFragment)
        }

        //loadSettings()
    }

    private fun loadSettings() {
        val sp = PreferenceManager.getDefaultSharedPreferences(requireActivity())

        val jacketNeeded = sp.getInt("jacket_needed", 30)
    }

    private fun setWeather(chanceOfRain: Float, windSpeed: Int) {
        binding.wvWeatherView.apply {
            setWeatherData(PrecipType.RAIN)
            speed = 250
            emissionRate = chanceOfRain
            angle = windSpeed
            fadeOutPercent = 1f
            colour = Color.parseColor("#0214fa")
            scaleFactor = 5f
        }
    }

    private fun createChanceOfRainText(weather: WeatherForecast) {
        binding.currentChanceRain.text = createLocationSpannable(weather)
        binding.currentChanceRain.movementMethod = LinkMovementMethod.getInstance()
        binding.currentChanceRain.highlightColor = Color.TRANSPARENT
    }

    private fun createLocationSpannable(weather: WeatherForecast): Spannable {

        val chanceOfRain = weather.weatherToDisplay.chanceOfRain
        val location = weather.locationName
        var chanceOfRainText = getString(R.string.current_chance_rain, chanceOfRain, location)

        val locationSpannable = SpannableStringBuilder(chanceOfRainText)
        val highlightStartPos = chanceOfRainText.length - location.length
        val highlightEndPos = chanceOfRainText.length

        locationSpannable.setSpan(
            ForegroundColorSpan(Color.GREEN),
            highlightStartPos, highlightEndPos,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )

        locationSpannable.setSpan(
            UnderlineSpan(),
            highlightStartPos, highlightEndPos,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )

        val changeLocation = object : ClickableSpan() {
            override fun onClick(view: View) {
                findNavController().
                navigate(R.id.action_jacketFragment_to_locationFragment)
            }
        }

        locationSpannable.setSpan(
            changeLocation,
            highlightStartPos, highlightEndPos,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )

        return locationSpannable
    }
}