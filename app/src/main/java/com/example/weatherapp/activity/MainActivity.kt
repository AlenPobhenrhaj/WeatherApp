package com.example.weatherapp.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapter.MinutelyForecastAdapter
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.viewmodel.WeatherViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels() {
        ViewModelProvider.NewInstanceFactory()
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
