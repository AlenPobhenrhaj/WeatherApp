package com.example.weatherapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherbitApi {
    private const val BASE_URL = "https://api.weatherbit.io/v2.0/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: WeatherbitApiService by lazy {
        retrofit.create(WeatherbitApiService::class.java)
    }
}
