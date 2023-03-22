package com.example.weatherapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "minutely_forecast")
data class MinutelyForecast(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "timestamp")
    val timestamp: Date,

    @ColumnInfo(name = "precipitation")
    val precipitation: Double,

    @ColumnInfo(name = "temperature")
    val temperature: Double,

    @ColumnInfo(name = "humidity")
    val humidity: Int,

    @ColumnInfo(name = "pressure")
    val pressure: Double,

    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double,

    @ColumnInfo(name = "wind_direction")
    val windDirection: Int
)
