package com.example.chcweather.data.source.repository

import com.example.chcweather.data.model.Weather
import com.example.chcweather.data.model.WeatherForecast

interface WeatherRepository {

    fun getWeather(): Weather

    fun getForecastWeather(): List<WeatherForecast>

    fun getSearchWeather(): Weather

    fun storeWeatherData(weather: Weather)

    fun storeForecastData(forecasts: List<WeatherForecast>)

    fun deleteWeatherData()

    fun deleteForecastData()

}