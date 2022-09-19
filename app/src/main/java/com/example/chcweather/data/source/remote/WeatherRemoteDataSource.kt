package com.example.chcweather.data.source.remote

import com.example.chcweather.data.model.LocationModel
import com.example.chcweather.data.model.NetworkWeather
import com.example.chcweather.data.model.NetworkWeatherForecast
import com.example.chcweather.utils.Result

interface WeatherRemoteDataSource {
    suspend fun getWeather(location: LocationModel): Result<NetworkWeather>

    suspend fun getWeatherForecast(location: LocationModel): Result<List<NetworkWeatherForecast>>

    suspend fun getSearchWeather(query: String): Result<NetworkWeather>

}