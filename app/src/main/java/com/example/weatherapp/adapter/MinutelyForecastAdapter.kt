package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemMinutelyForecastBinding
import com.example.weatherapp.model.MinutelyForecast

class MinutelyForecastAdapter : RecyclerView.Adapter<MinutelyForecastAdapter.MinutelyForecastViewHolder>() {

    private var forecasts: List<MinutelyForecast> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MinutelyForecastViewHolder {
        val binding = ItemMinutelyForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MinutelyForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MinutelyForecastViewHolder, position: Int) {
        holder.bind(forecasts[position])
    }

    override fun getItemCount(): Int = forecasts.size

    fun submitList(newForecasts: List<MinutelyForecast>) {
        forecasts = newForecasts
        notifyDataSetChanged()
    }

    inner class MinutelyForecastViewHolder(private val binding: ItemMinutelyForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecast: MinutelyForecast) {
            binding.forecast = forecast
            binding.executePendingBindings()
        }
    }
}
