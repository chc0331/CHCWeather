package com.example.chcweather.data.source.local

import com.example.chcweather.data.source.local.dao.WeatherDao
import com.example.chcweather.data.source.local.entity.DBWeather
import com.example.chcweather.data.source.local.entity.DBWeatherForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherLocalDataSourceImpl @Inject constructor(
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

    override suspend fun getForecastWeather(): List<DBWeatherForecast>? {
        return null
    }

    override suspend fun saveForecastWeather(weatherForecast: DBWeatherForecast) {

    }

    override suspend fun deleteForecastWeather() {

    }
}