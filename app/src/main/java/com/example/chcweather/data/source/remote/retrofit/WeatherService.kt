package com.example.chcweather.data.source.remote.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherService {

    private const val BASE_URL = "https://api.openweathermap.org"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(WeatherApiService::class.java)
}