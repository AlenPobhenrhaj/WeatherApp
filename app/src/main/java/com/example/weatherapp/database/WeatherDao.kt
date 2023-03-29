package com.example.weatherapp.database

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.DailyForecast

/*@Dao
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
}*/

@Dao
interface DailyForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dailyForecasts: List<DailyForecast>)

    @Query("SELECT * FROM daily_forecast")
    suspend fun getAllDailyForecasts(): List<DailyForecast>

    @Query("SELECT * FROM daily_forecast WHERE timezone = :timezone ORDER BY timestamp ASC")
    suspend fun getAllDailyForecastsByLocation(timezone: String): List<DailyForecast>

    suspend fun getAllDailyForecastsByLocationWithLogging(city: String): List<DailyForecast> {
        val forecasts = getAllDailyForecastsByLocation(city)
        Log.d("DailyForecastDao", "Fetched daily forecasts by location ($city): $forecasts")
        return forecasts
    }

    @Query("SELECT * FROM daily_forecast WHERE timezone = :timezone ORDER BY timestamp ASC")
    suspend fun getAllDailyForecastsByTimezone(timezone: String): List<DailyForecast>
}
