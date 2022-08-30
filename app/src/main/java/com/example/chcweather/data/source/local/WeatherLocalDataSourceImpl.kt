package com.example.chcweather.data.source.local

import com.example.chcweather.data.source.local.dao.WeatherDao
import com.example.chcweather.data.source.local.entity.DBWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherLocalDataSourceImpl(
    private val weatherDao: WeatherDao
) : WeatherLocalDataSource {

    override suspend fun getWeather(): DBWeather? = withContext(Dispatchers.IO) {
        return@withContext weatherDao.getWeather()
    }

    override suspend fun saveWeather(weather: DBWeather) = withContext(Dispatchers.IO) {
        weatherDao.insertWeather(weather)
    }

    override suspend fun deleteWeather() = withContext(Dispatchers.IO) {
        weatherDao.deleteAllWeather()
    }
}