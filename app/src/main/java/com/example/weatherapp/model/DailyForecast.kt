package com.example.weatherapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "daily_forecast")
data class DailyForecast(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "timestamp")
    val timestamp: Date,

    @ColumnInfo(name = "precipitation")
    val precipitation: Double,

    @ColumnInfo(name = "temperature")
    val temperature: Double,

    @ColumnInfo(name = "weather_description")
    val weatherDescription: String,

    @ColumnInfo(name = "timezone")
    val timezone: String,

    @ColumnInfo(name = "datetime")
    val datetime: String
    )