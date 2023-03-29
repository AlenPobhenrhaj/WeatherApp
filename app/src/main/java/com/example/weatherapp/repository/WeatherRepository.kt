package com.example.weatherapp.repository

import android.util.Log
import com.example.weatherapp.data.WeatherbitApiService
import com.example.weatherapp.database.DailyForecastDao
import com.example.weatherapp.model.*
import java.util.*

class WeatherRepository(
    private val apiService: WeatherbitApiService,
    private val dailyForecastDao: DailyForecastDao
) {

    suspend fun fetchDailyForecast(city: String, country: String, apiKey: String) {
        try {
            val forecastResponse = apiService.getDailyForecast(city, country, apiKey)
            Log.d("WeatherRepository", "Fetched daily forecast: $forecastResponse")
            val forecastList = convertToDailyForecastList(forecastResponse)
            dailyForecastDao.insertAll(forecastList)
            Log.d("WeatherRepository", "Stored daily forecasts")
        } catch (e: Exception) {
            // Handle exceptions, e.g., no network connection or API errors
        }
    }


    private fun convertToDailyForecastList(forecastResponse: WeatherData): List<DailyForecast> {
        val dailyForecasts = forecastResponse.data.map { data ->
            DailyForecast(
                id = 0,
                timestamp = Date(data.ts.toLong() * 1000),
                precipitation = data.precip,
                temperature = data.temp,
                weatherDescription = data.weather.description,
                timezone = forecastResponse.timezone,
                datetime = data.datetime
            )
        }
        Log.d("WeatherRepository", "Converted daily forecasts: $dailyForecasts")
        return dailyForecasts
    }

    suspend fun getAllDailyForecastsByLocationWithLogging(timezone: String): List<DailyForecast> {
        Log.d("WeatherRepository", "Fetching daily forecasts for $timezone from the database")
        val dailyForecasts = dailyForecastDao.getAllDailyForecastsByLocationWithLogging(timezone)
        Log.d("WeatherRepository", "Fetched daily forecasts: $dailyForecasts")
        return dailyForecasts
    }

    suspend fun getAllDailyForecastsByTimezone(timezone: String): List<DailyForecast> {
        return dailyForecastDao.getAllDailyForecastsByTimezone(timezone)
    }


}
