package com.example.chcweather.data.source.local

import com.example.chcweather.data.source.local.entity.DBWeather

interface WeatherLocalDataSource {

    suspend fun getWeather(): DBWeather?

    suspend fun saveWeather(weather: DBWeather)

    suspend fun deleteWeather()
}