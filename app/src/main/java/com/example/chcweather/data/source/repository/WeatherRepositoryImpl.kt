package com.example.chcweather.data.source.repository

import com.example.chcweather.data.model.*
import com.example.chcweather.data.source.remote.WeatherRemoteDataSource
import com.example.chcweather.mapper.WeatherMapperRemote
import com.example.chcweather.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(private val remoteDataSource: WeatherRemoteDataSource) :
    WeatherRepository {

    override suspend fun getWeather(location: LocationModel, refresh: Boolean): Result<Weather?> =
        withContext(Dispatchers.IO) {
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
        }

//    override fun getWeather(): Weather {
//        val wind = Wind(2.0, 10)
//        val networkWeatherDescription =
//            NetworkWeatherDescription(0L, "Clouds", "description", "icon")
//        val networkWeatherCondition = NetworkWeatherCondition(34.0, 22.0, 10.0)
//        return Weather(
//            0,
//            0,
//            "Mountain View",
//            wind,
//            listOf(networkWeatherDescription),
//            networkWeatherCondition
//        )
//    }

    override fun getForecastWeather(): List<WeatherForecast> {
        val wind = Wind(2.0, 10)
        val networkWeatherDescription =
            NetworkWeatherDescription(0L, "Clouds", "description", "icon")
        val networkWeatherCondition = NetworkWeatherCondition(34.0, 22.0, 10.0)
        return listOf(
            WeatherForecast(
                0, "0", wind, listOf(networkWeatherDescription),
                networkWeatherCondition
            ), WeatherForecast(
                0, "0", wind, listOf(networkWeatherDescription),
                networkWeatherCondition
            ), WeatherForecast(
                0, "0", wind, listOf(networkWeatherDescription),
                networkWeatherCondition
            ), WeatherForecast(
                0, "0", wind, listOf(networkWeatherDescription),
                networkWeatherCondition
            )
        )
    }

    override fun getSearchWeather(): Weather {
        val wind = Wind(2.0, 10)
        val networkWeatherDescription = NetworkWeatherDescription(0L, "main", "description", "icon")
        val networkWeatherCondition = NetworkWeatherCondition(36.0, 22.0, 10.0)
        return Weather(
            0,
            0,
            "Sunny",
            wind,
            listOf(networkWeatherDescription),
            networkWeatherCondition
        )
    }

    override fun storeWeatherData(weather: Weather) {

    }

    override fun storeForecastData(forecasts: List<WeatherForecast>) {

    }

    override fun deleteWeatherData() {

    }

    override fun deleteForecastData() {

    }
}