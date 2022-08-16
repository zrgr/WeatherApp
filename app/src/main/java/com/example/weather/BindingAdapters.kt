package com.example.weather

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.weather.WeatherApiStatus


@BindingAdapter("weatherApiStatus")
fun bindStatus(statusImageView: ImageView,
               status: WeatherApiStatus) {
    when (status) {
        WeatherApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        WeatherApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        WeatherApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}