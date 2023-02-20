package com.example.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.WeatherViewModel
import com.example.weather.databinding.FragmentLocationBinding



class LocationFragment : Fragment() {

    private val viewModel: WeatherViewModel by activityViewModels()
    lateinit var binding: FragmentLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.findButton.setOnClickListener { setLocation(binding.location.text.toString()) }

        return binding.root
    }

    private fun setLocation(location: String) {
        findNavController()
            .navigate(R.id.action_locationFragment_to_jacketFragment)

    }
}