package com.example.weatherapp.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private const val DATABASE_NAME = "weather_db"

    @Volatile
    private var INSTANCE: WeatherDatabase? = null

    fun getDatabase(context: Context): WeatherDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java,
                DATABASE_NAME
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
