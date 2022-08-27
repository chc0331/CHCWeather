package com.example.chcweather.data.source.repository

import com.example.chcweather.data.model.Weather
import com.example.chcweather.data.model.WeatherForecast

class WeatherRepositoryImpl : WeatherRepository {
    val NONE = Weather("None", "None")
    override fun getWeather(): Weather {
        return Weather("Daegu", "Sunny")
    }

    override fun getForecastWeather(): List<WeatherForecast> {
        return emptyList()
    }

    override fun getSearchWeather(): Weather {
        return NONE
    }

    override fun storeWeatherData(weather: Weather) {

    }

    override fun storeForecastData(forecasts: List<WeatherForecast>) {

    }

    override fun deleteWeatherData() {

    }

    override fun deleteForecastData() {

    }
}