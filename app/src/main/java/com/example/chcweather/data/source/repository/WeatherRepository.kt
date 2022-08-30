package com.example.chcweather.data.source.repository

import com.example.chcweather.data.model.LocationModel
import com.example.chcweather.data.model.Weather
import com.example.chcweather.utils.Result

interface WeatherRepository {

    suspend fun getWeather(location: LocationModel, refresh: Boolean): Result<Weather?>

    suspend fun storeWeatherData(weather: Weather)

    suspend fun deleteWeatherData()

}