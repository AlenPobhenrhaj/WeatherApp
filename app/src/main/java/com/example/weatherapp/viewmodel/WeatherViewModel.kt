package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.weatherapp.model.DailyForecast
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow


class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _dailyForecasts = MutableStateFlow<List<DailyForecast>>(emptyList())
    val dailyForecasts: LiveData<List<DailyForecast>> = _dailyForecasts.asLiveData()


    init {
        fetchDailyForecast("Kuala Lumpur")
    }

    fun fetchDailyForecast(city: String) {
        viewModelScope.launch {
            val country = getCountryCodeForCity(city)
            repository.fetchDailyForecast(city, country, "0b326fe2adf846caaf50076157562425")

            val timezone = getCityTimezone(city)
            val forecasts = repository.getAllDailyForecastsByTimezone(timezone)
            Log.d("WeatherViewModel", "Fetched daily forecasts for $city: $forecasts")
            _dailyForecasts.value = forecasts
        }
    }

    private fun getCountryCodeForCity(city: String): String {
        return when (city) {
            "Kuala Lumpur" -> "MY"
            "New York" -> "US"
            "London" -> "GB"
            "Tokyo" -> "JP"
            else -> "MY" // Default to Malaysia
        }
    }

    private fun getCityTimezone(city: String): String {
        return when (city) {
            "Kuala Lumpur" -> "Asia/Kuala_Lumpur"
            "New York" -> "America/New_York"
            "London" -> "Europe/London"
            "Tokyo" -> "Asia/Tokyo"
            else -> "Asia/Kuala_Lumpur" // Default to Kuala Lumpur timezone
        }
    }
}


