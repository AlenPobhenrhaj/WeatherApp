package com.example.weatherapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.CurrentWeather
import com.example.weatherapp.model.MinutelyForecast

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currentWeather: CurrentWeather)

    @Query("SELECT * FROM current_weather WHERE id = :id")
    suspend fun getCurrentWeather(id: Int): CurrentWeather?
}

@Dao
interface MinutelyForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(forecastList: List<MinutelyForecast>)

    @Query("SELECT * FROM minutely_forecast")
    suspend fun getAllMinutelyForecasts(): List<MinutelyForecast>
}
