package com.example.chcweather.data.model

import com.google.gson.annotations.SerializedName

data class NetworkWeatherForecast(
    val id: Int,
    @SerializedName("main")
    val networkWeatherCondition: NetworkWeatherCondition,
    @SerializedName("weather")
    val networkWeatherDescription: List<NetworkWeatherDescription>,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("dt_txt")
    val date: String,
)
