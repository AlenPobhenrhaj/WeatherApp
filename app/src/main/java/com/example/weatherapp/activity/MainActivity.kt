package com.example.weatherapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapter.DailyForecastAdapter
import com.example.weatherapp.adapter.MinutelyForecastAdapter
import com.example.weatherapp.data.WeatherbitApi
import com.example.weatherapp.database.DatabaseProvider
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.model.DailyForecast
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.viewmodel.WeatherViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

    private val dailyForecastAdapter = DailyForecastAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = dailyForecastAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.dailyForecasts.observe(this) { dailyForecasts ->
            dailyForecastAdapter.submitList(dailyForecasts)
        }
    }
}


