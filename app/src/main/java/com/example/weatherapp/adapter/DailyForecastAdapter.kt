package com.example.weatherapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemDailyForecastBinding
import com.example.weatherapp.model.DailyForecast

class DailyForecastAdapter : RecyclerView.Adapter<DailyForecastAdapter.DailyForecastViewHolder>() {

    private var forecasts: List<DailyForecast> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val binding = ItemDailyForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.bind(forecasts[position])
    }

    override fun getItemCount(): Int = forecasts.size

    fun submitList(newForecasts: List<DailyForecast>) {
        Log.d("DailyForecastAdapter", "Submitting new forecasts: $newForecasts")
        forecasts = newForecasts
        notifyDataSetChanged()
    }

    inner class DailyForecastViewHolder(private val binding: ItemDailyForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecast: DailyForecast) {
            binding.forecast = forecast
            binding.executePendingBindings()
        }
    }


}
