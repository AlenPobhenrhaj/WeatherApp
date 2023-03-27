package com.example.weatherapp.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.model.CurrentWeather
import com.example.weatherapp.model.DailyForecast
import com.example.weatherapp.model.MinutelyForecast
import com.example.weatherapp.utils.Converters

@Database(
    entities = [DailyForecast::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    /*abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun minutelyForecastDao(): MinutelyForecastDao*/
    abstract fun dailyForecastDao(): DailyForecastDao
}
