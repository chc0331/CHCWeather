package com.example.chcweather.data.source.repository

import com.example.chcweather.data.model.LocationModel
import com.example.chcweather.data.model.Weather
import com.example.chcweather.data.model.WeatherForecast
import com.example.chcweather.data.source.local.WeatherLocalDataSource
import com.example.chcweather.data.source.remote.WeatherRemoteDataSource
import com.example.chcweather.mapper.WeatherForecastMapperRemote
import com.example.chcweather.mapper.WeatherMapperLocal
import com.example.chcweather.mapper.WeatherMapperRemote
import com.example.chcweather.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource
) : WeatherRepository {

    override suspend fun getWeather(location: LocationModel, refresh: Boolean): Result<Weather?> =
        withContext(Dispatchers.IO) {
            if (refresh) {
                val mapper = WeatherMapperRemote()
                when (val response = remoteDataSource.getWeather(location)) {
                    is Result.Success -> {
                        if (response.data != null) {
                            Result.Success(mapper.transformToDomain(response.data))
                        } else {
                            Result.Success(null)
                        }
                    }
                    is Result.Error -> {
                        Result.Error(response.exception)
                    }
                    else -> Result.Loading
                }
            } else {
                val mapper = WeatherMapperLocal()
                val weather = localDataSource.getWeather()
                if (weather != null) {
                    Result.Success(mapper.transformToDomain(weather))
                } else {
                    Result.Success(null)
                }
            }
        }

    override suspend fun storeWeatherData(weather: Weather) {
        val mapper = WeatherMapperLocal()
        localDataSource.saveWeather(mapper.transformToDto(weather))
    }

    override suspend fun deleteWeatherData() {
        localDataSource.deleteWeather()
    }

    override suspend fun getForecastWeather(
        location: LocationModel,
        refresh: Boolean
    ): Result<List<WeatherForecast>?> =
        withContext(Dispatchers.IO) {
            if (refresh) {
                val mapper = WeatherForecastMapperRemote()
                when (val response = remoteDataSource.getWeatherForecast(location)) {
                    is Result.Success -> {
                        if (response.data != null) {
                            Result.Success(mapper.transformToDomain(response.data))
                        } else {
                            Result.Success(null)
                        }
                    }
                    is Result.Error -> {
                        Result.Error(response.exception)
                    }
                    else -> Result.Loading
                }
            } else {
                Result.Success(null)
            }
        }

    override suspend fun storeForecastData(forecasts: List<WeatherForecast>) {

    }

    override suspend fun deleteForecastData() {

    }

    override suspend fun getSearchWeather(location: String): Result<Weather?> =
        withContext(Dispatchers.IO) {
            val mapper = WeatherMapperRemote()
            return@withContext when (val response = remoteDataSource.getSearchWeather(location)) {
                is Result.Success -> {
                    if (response.data != null) {
                        Result.Success(mapper.transformToDomain(response.data))
                    } else {
                        Result.Success(null)
                    }
                }
                is Result.Error -> {
                    Result.Error(response.exception)
                }
                else -> {
                    Result.Loading
                }
            }
        }
}