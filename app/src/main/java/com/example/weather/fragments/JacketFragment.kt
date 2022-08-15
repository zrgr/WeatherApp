package com.example.weather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.weather.R
import com.example.weather.WeatherViewModel
import com.example.weather.databinding.FragmentJacketBinding

class JacketFragment : Fragment() {

    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentJacketBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.currentChanceRain.text = getString(R.string.current_chance_rain, viewModel.currentChanceOfRain.value.toString())

        return binding.root
    }

}