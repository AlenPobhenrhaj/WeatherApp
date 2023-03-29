package com.example.weatherapp.utils

import android.app.Application
import androidx.room.Room
import com.example.weatherapp.database.WeatherDatabase

class MyApplication : Application() {
    lateinit var weatherDatabase: WeatherDatabase

    override fun onCreate() {
        super.onCreate()
        weatherDatabase = Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java, "weather_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
