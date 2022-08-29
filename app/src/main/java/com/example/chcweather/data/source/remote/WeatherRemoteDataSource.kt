package com.example.chcweather.data.source.remote

import com.example.chcweather.data.model.LocationModel
import com.example.chcweather.data.model.NetworkWeather
import com.example.chcweather.utils.Result

interface WeatherRemoteDataSource {
    suspend fun getWeather(location: LocationModel): Result<NetworkWeather>
}