package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.weatherapp.adapter.DailyForecastAdapter
import com.example.weatherapp.data.WeatherbitApi
import com.example.weatherapp.database.DatabaseProvider
import com.example.weatherapp.database.WeatherDatabase
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.viewmodel.WeatherViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DailyForecastAdapter
    lateinit var weatherDatabase: WeatherDatabase

    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dailyForecastDao = DatabaseProvider.getDatabase(applicationContext).dailyForecastDao()
                val apiService = WeatherbitApi.apiService
                val repository =
                    WeatherRepository(apiService, dailyForecastDao)
                @Suppress("UNCHECKED_CAST")
                return WeatherViewModel(repository) as T
            }
        })[WeatherViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        adapter = DailyForecastAdapter()
        binding.recyclerView.adapter = adapter

        weatherDatabase = Room.databaseBuilder(
            this,
            WeatherDatabase::class.java, "weather_database"
        )
            .fallbackToDestructiveMigration()
            .build()

        viewModel.dailyForecasts.observe(this, Observer { forecasts ->
            Log.d("MainActivity", "Observed daily forecasts: $forecasts")
            adapter.submitList(forecasts)
        })


        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("Kuala Lumpur", "New York", "London", "Tokyo")
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.locationSpinner.adapter = spinnerAdapter

        binding.locationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                viewModel.fetchDailyForecast(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }
}
