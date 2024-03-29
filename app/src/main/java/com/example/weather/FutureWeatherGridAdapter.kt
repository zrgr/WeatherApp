package com.example.weather

import android.content.Context
import android.provider.Settings.System.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.FutureWeatherBinding
import com.example.weather.models.app.Weather

class FutureWeatherGridAdapter (
    val clickListener: FutureWeatherListener
) : ListAdapter<Weather,
FutureWeatherGridAdapter.FutureWeatherViewHolder>(DiffCallback) {

    class FutureWeatherViewHolder(
        private var binding: FutureWeatherBinding,
        private var context: Context
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: Weather, clickListener: FutureWeatherListener) {
            binding.weather = weather
            binding.chanceOfRain.text = context.resources.getString(R.string.future_chance_of_rain, weather.chanceOfRain, weather.time)
            binding.clickListener = clickListener
            binding.weatherType.setImageResource(weather.weatherTypeImage)
            binding.weatherTypeDescription.text = context.resources.getString(weather.weatherTypeDescription)
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Weather>() {
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
            LayoutInflater.from(parent.context), parent, false), parent.context)
    }

    override fun onBindViewHolder(holder: FutureWeatherGridAdapter.FutureWeatherViewHolder, position: Int) {
        val weather = getItem(position)
        holder.bind(weather, clickListener)
    }
}

class FutureWeatherListener(val clickListener: (weather: Weather) -> Unit) {
    fun onClick(weather: Weather) = clickListener(weather)
}
