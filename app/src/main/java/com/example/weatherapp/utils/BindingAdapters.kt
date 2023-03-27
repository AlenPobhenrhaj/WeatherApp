package com.example.weatherapp.utils


import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.*

@BindingAdapter("temperatureText")
fun bindTemperatureText(textView: TextView, temperature: Double) {
    textView.text = String.format("Temperature: %.1f°C", temperature)
}

@BindingAdapter("precipitationText")
fun bindPrecipitationText(view: TextView, precipitation: Double) {
    view.text = String.format(Locale.getDefault(), "Precipitation: %.1fmm", precipitation)
}