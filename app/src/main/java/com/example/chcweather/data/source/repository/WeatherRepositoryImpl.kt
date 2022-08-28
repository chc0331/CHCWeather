package com.example.chcweather.data.source.repository

import com.example.chcweather.data.model.*

class WeatherRepositoryImpl : WeatherRepository {
    override fun getWeather(): Weather {
        val wind = Wind(2.0, 10)
        val networkWeatherDescription =
            NetworkWeatherDescription(0L, "Clouds", "description", "icon")
        val networkWeatherCondition = NetworkWeatherCondition(34.0, 22.0, 10.0)
        return Weather(
            0,
            0,
            "Mountain View",
            wind,
            listOf(networkWeatherDescription),
            networkWeatherCondition
        )
    }

    override fun getForecastWeather(): List<WeatherForecast> {
        val wind = Wind(2.0, 10)
        val networkWeatherDescription =
            NetworkWeatherDescription(0L, "Clouds", "description", "icon")
        val networkWeatherCondition = NetworkWeatherCondition(34.0, 22.0, 10.0)
        return listOf(
            WeatherForecast(
                0, "0", wind, listOf(networkWeatherDescription),
                networkWeatherCondition
            ), WeatherForecast(
                0, "0", wind, listOf(networkWeatherDescription),
                networkWeatherCondition
            ), WeatherForecast(
                0, "0", wind, listOf(networkWeatherDescription),
                networkWeatherCondition
            ), WeatherForecast(
                0, "0", wind, listOf(networkWeatherDescription),
                networkWeatherCondition
            )
        )
    }

    override fun getSearchWeather(): Weather {
        val wind = Wind(2.0, 10)
        val networkWeatherDescription = NetworkWeatherDescription(0L, "main", "description", "icon")
        val networkWeatherCondition = NetworkWeatherCondition(36.0, 22.0, 10.0)
        return Weather(
            0,
            0,
            "Sunny",
            wind,
            listOf(networkWeatherDescription),
            networkWeatherCondition
        )
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