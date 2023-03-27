package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.weatherapp.model.CurrentWeather
import com.example.weatherapp.model.DailyForecast
import com.example.weatherapp.model.MinutelyForecast
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow


class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _dailyForecasts = MutableStateFlow<List<DailyForecast>>(emptyList())
    val dailyForecasts: LiveData<List<DailyForecast>> = _dailyForecasts.asLiveData()

 /*   private val _timezone = MutableStateFlow<String>("")
    val timezone: LiveData<String> = _timezone.asLiveData()*/

    init {
        fetchDailyForecast()
    }

    private fun fetchDailyForecast() {
        viewModelScope.launch {
            val forecasts = repository.getAllDailyForecasts()
            if (forecasts.isEmpty()) {
                repository.fetchDailyForecast("Kuala Lumpur", "Malaysia", "0b326fe2adf846caaf50076157562425")
                _dailyForecasts.value = repository.getAllDailyForecasts()
            } else {
                _dailyForecasts.value = forecasts
            }
        }
    }
}

