package com.example.weatherapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapter.MinutelyForecastAdapter
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.data.WeatherbitApi
import com.example.weatherapp.database.DatabaseProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val currentWeatherDao =
                    DatabaseProvider.getDatabase(applicationContext).currentWeatherDao()
                val minutelyForecastDao =
                    DatabaseProvider.getDatabase(applicationContext).minutelyForecastDao()
                val apiService = WeatherbitApi.apiService
                val repository =
                    WeatherRepository(apiService, currentWeatherDao, minutelyForecastDao)
                @Suppress("UNCHECKED_CAST")
                return WeatherViewModel(repository) as T
            }
        })[WeatherViewModel::class.java]
    }

    private val minutelyForecastAdapter = MinutelyForecastAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupRecyclerView()
        observeData()
    }

    private fun setupRecyclerView() {
        binding.minutelyForecastList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = minutelyForecastAdapter
        }
    }

    private fun observeData() {
        viewModel.minutelyForecasts.observe(this) { forecasts ->
            minutelyForecastAdapter.submitList(forecasts)
        }
    }
}




