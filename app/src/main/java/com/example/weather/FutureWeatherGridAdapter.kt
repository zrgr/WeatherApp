package com.example.weather

import com.example.weather.models.app.WeatherForecast
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.FutureWeatherBinding
import com.example.weather.models.app.Weather

class FutureWeatherGridAdapter : ListAdapter<Weather,
        FutureWeatherGridAdapter.FutureWeatherViewHolder>(DiffCallback) {

    class FutureWeatherViewHolder(
        private var binding: FutureWeatherBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: Weather) {
            binding.jacketNeeded = weather.jacketNeeded.toString()
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<WeatherForecast>() {
        override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem.time == newItem.time
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FutureWeatherGridAdapter.FutureWeatherViewHolder {
        return FutureWeatherViewHolder(FutureWeatherBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FutureWeatherGridAdapter.FutureWeatherViewHolder, position: Int) {
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto)
    }
}