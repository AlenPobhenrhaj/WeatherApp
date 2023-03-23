package com.example.weatherapp.repository

import com.example.weatherapp.data.WeatherbitApiService
import com.example.weatherapp.database.CurrentWeatherDao
import com.example.weatherapp.database.MinutelyForecastDao
import com.example.weatherapp.model.*
import java.util.*

class WeatherRepository(
    private val apiService: WeatherbitApiService,
    private val currentWeatherDao: CurrentWeatherDao,
    private val minutelyForecastDao: MinutelyForecastDao
) {
    suspend fun fetchCurrentWeather(city: String, country: String, apiKey: String) {
        try {
            val forecastResponse = apiService.getCurrentWeather(city, country, apiKey)
            val currentWeather = convertToCurrentWeather(forecastResponse)
            currentWeatherDao.insert(currentWeather)
        } catch (e: Exception) {
            // Handle exceptions, e.g., no network connection or API errors
        }
    }

    private fun convertToCurrentWeather(forecastResponse: WeatherData): CurrentWeather {
        // Assuming WeatherData has properties like "temperature", "humidity", "pressure", etc.,
        // and CurrentWeather has a constructor that accepts these properties as parameters.
        val data = forecastResponse.data.first()
        return CurrentWeather(
            id = 0, // Replace with an appropriate ID if needed.
            name = forecastResponse.city_name,
            temperature = forecastResponse.data.first().temp,
            humidity = forecastResponse.data.first().rh,
            pressure = forecastResponse.data.first().pres.toInt(),
            description = forecastResponse.data.first().weather.description,
            icon = forecastResponse.data.first().weather.icon,
            windSpeed = forecastResponse.data.first().wind_spd,
            windDirection = forecastResponse.data.first().wind_dir
        )
    }

    suspend fun fetchMinutelyForecast(city: String, country: String, apiKey: String) {
        try {
            val forecastResponse = apiService.getMinutelyForecast(city, country, apiKey)
            val forecastList = convertToMinutelyForecastList(forecastResponse)
            minutelyForecastDao.insertAll(forecastList)
        } catch (e: Exception) {
            // Handle exceptions, e.g., no network connection or API errors
        }
    }

    private fun convertToMinutelyForecastList(forecastResponse: WeatherData): List<MinutelyForecast> {
        return forecastResponse.data.map { data ->
            MinutelyForecast(
                id = 0, // Replace with an appropriate ID if needed.
                timestamp = Date(data.ts.toLong() * 1000),
                precipitation = data.precip,
                temperature = data.temp,
                humidity = data.rh,
                pressure = data.pres,
                windSpeed = data.wind_spd,
                windDirection = data.wind_dir
            )
        }
    }


    suspend fun getCurrentWeather(id: Int): CurrentWeather? {
        return currentWeatherDao.getCurrentWeather(id)
    }

    suspend fun getAllMinutelyForecasts(): List<MinutelyForecast> {
        return minutelyForecastDao.getAllMinutelyForecasts()
    }
}
