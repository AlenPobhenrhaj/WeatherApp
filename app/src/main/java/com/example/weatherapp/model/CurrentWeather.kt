package com.example.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherapp.utils.Converters

@Entity(tableName = "current_weather")
@TypeConverters(Converters::class)
data class CurrentWeather(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val temperature: Double,
    val humidity: Int, // Changed type from List<Data> to Int
    val pressure: Int,
    val description: String,
    val icon: String,
    val windSpeed: Double,
    val windDirection: Int
)

