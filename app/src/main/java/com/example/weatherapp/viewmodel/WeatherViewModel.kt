package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.weatherapp.model.CurrentWeather
import com.example.weatherapp.model.MinutelyForecast
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow


class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _currentWeather = MutableStateFlow<CurrentWeather?>(null)
    val currentWeather: LiveData<CurrentWeather?> = _currentWeather.asLiveData()

    private val _minutelyForecasts = MutableStateFlow<List<MinutelyForecast>>(emptyList())
    val minutelyForecasts: LiveData<List<MinutelyForecast>> = _minutelyForecasts.asLiveData()

    init {
        fetchCurrentWeather()
        fetchMinutelyForecast()
    }

    private fun fetchCurrentWeather() {
        viewModelScope.launch {
            repository.fetchCurrentWeather("Kuala Lumpur", "Malaysia", "0b326fe2adf846caaf50076157562425")
            val weather = repository.getCurrentWeather(1)
            _currentWeather.value = weather
        }
    }

    private fun fetchMinutelyForecast() {
        viewModelScope.launch {
            repository.fetchMinutelyForecast("Kuala Lumpur", "Malaysia", "0b326fe2adf846caaf50076157562425")
            val forecasts = repository.getAllMinutelyForecasts()
            _minutelyForecasts.value = forecasts
        }
    }

}

