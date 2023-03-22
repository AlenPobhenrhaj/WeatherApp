package com.example.weatherapp.utils

import androidx.room.TypeConverter
import com.example.weatherapp.model.Data
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {
    @TypeConverter
    fun fromDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun toDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromDataList(value: String?): List<Data>? {
        val listType = object : TypeToken<List<Data>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toDataList(dataList: List<Data>?): String? {
        return Gson().toJson(dataList)
    }
}
