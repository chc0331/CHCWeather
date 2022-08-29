package com.example.chcweather.data.source.repository

import com.example.chcweather.data.model.LocationModel
import com.example.chcweather.data.model.Weather
import com.example.chcweather.data.model.WeatherForecast
import com.example.chcweather.utils.Result

interface WeatherRepository {

    suspend fun getWeather(location: LocationModel, refresh: Boolean): Result<Weather?>

    fun getForecastWeather(): List<WeatherForecast>

    fun getSearchWeather(): Weather

    fun storeWeatherData(weather: Weather)

    fun storeForecastData(forecasts: List<WeatherForecast>)

    fun deleteWeatherData()

    fun deleteForecastData()

}