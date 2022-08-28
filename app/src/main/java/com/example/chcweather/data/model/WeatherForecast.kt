package com.example.chcweather.data.model

data class WeatherForecast(
    val uID: Int,
    var date: String,
    var wind: Wind,
    val networkWeatherDescription: List<NetworkWeatherDescription>,
    val networkWeatherCondition: NetworkWeatherCondition
)
