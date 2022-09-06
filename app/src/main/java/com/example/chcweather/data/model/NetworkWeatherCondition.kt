package com.example.chcweather.data.model

data class NetworkWeatherCondition(
    var temp: Double,
    val pressure: Double,
    val humidity: Double
) {
}