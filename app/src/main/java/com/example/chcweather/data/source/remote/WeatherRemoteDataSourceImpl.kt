package com.example.chcweather.data.source.remote

import com.example.chcweather.data.model.LocationModel
import com.example.chcweather.data.model.NetworkWeather
import com.example.chcweather.data.source.remote.retrofit.WeatherApiService
import com.example.chcweather.utils.Result
import kotlinx.coroutines.*

class WeatherRemoteDataSourceImpl(private val apiService: WeatherApiService) :
    WeatherRemoteDataSource {

    companion object {
        private const val API_KEY = "7bda6f2d5004d31e6c8d02695b5b2799"
    }

    override suspend fun getWeather(location: LocationModel): Result<NetworkWeather> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val result = apiService.getCurrentWeather(
                    location.latitude, location.longitude, API_KEY
                )
                if (result.isSuccessful) {
                    val networkWeather = result.body()
                    Result.Success(networkWeather)
                } else {
                    Result.Success(null)
                }
            } catch (exception: Exception) {
                Result.Error(exception)
            }
        }
}