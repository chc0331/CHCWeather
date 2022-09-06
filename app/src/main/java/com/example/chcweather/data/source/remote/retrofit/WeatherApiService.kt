package com.example.chcweather.data.source.remote.retrofit

import com.example.chcweather.data.model.NetworkWeather
import com.example.chcweather.data.model.NetworkWeatherForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double, @Query("lon") longitude: Double,
        @Query("appid") appid: String
    ): Response<NetworkWeather>

    @GET("/data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("lat") latitude: Double, @Query("lon") longitude: Double,
        @Query("appid") appid: String
    ): Response<NetworkWeatherForecastResponse>

}