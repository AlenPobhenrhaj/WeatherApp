package com.example.weatherapp.data

import com.example.weatherapp.model.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherbitApiService {
    @GET("current")
    suspend fun getCurrentWeather(
        @Query("city") city: String,
        @Query("country") country: String,
        @Query("key") apiKey: String
    ): WeatherData

    @GET("forecast/minutely")
    suspend fun getMinutelyForecast(
        @Query("city") city: String,
        @Query("country") country: String,
        @Query("key") apiKey: String
    ): WeatherData
}

