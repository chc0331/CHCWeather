package com.example.chcweather.data.model

data class WeatherForecast(
    val uID: Int,
    val networkWeatherCondition: NetworkWeatherCondition,
    val networkWeatherDescription: List<NetworkWeatherDescription>,
    var wind: Wind,
    var date: String
)
