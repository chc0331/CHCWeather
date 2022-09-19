package com.example.chcweather.data.source.remote

import com.example.chcweather.data.model.LocationModel
import com.example.chcweather.data.model.NetworkWeather
import com.example.chcweather.data.model.NetworkWeatherForecast
import com.example.chcweather.data.source.remote.retrofit.WeatherApiService
import com.example.chcweather.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject
constructor(private val apiService: WeatherApiService) :
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

    override suspend fun getWeatherForecast(location: LocationModel): Result<List<NetworkWeatherForecast>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val result = apiService.getWeatherForecast(
                    location.latitude, location.longitude,
                    API_KEY
                )
                if (result.isSuccessful) {
                    val networkWeatherForecastResponse = result.body()
                    if (networkWeatherForecastResponse == null)
                        Result.Success(null)
                    else
                        Result.Success(networkWeatherForecastResponse.weathers)
                } else {
                    Result.Success(null)
                }
            } catch (exception: Exception) {
                Result.Error(exception)
            }
        }

    override suspend fun getSearchWeather(query: String): Result<NetworkWeather> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val result = apiService.getSearchWeather(query, API_KEY)
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