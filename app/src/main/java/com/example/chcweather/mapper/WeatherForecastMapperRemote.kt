package com.example.chcweather.mapper

import com.example.chcweather.data.model.NetworkWeatherForecast
import com.example.chcweather.data.model.WeatherForecast

class WeatherForecastMapperRemote :
    BaseMapper<List<NetworkWeatherForecast>, List<WeatherForecast>> {
    override fun transformToDomain(type: List<NetworkWeatherForecast>): List<WeatherForecast> {
        return type.map { networkWeatherForecast ->
            WeatherForecast(
                networkWeatherForecast.id,
                networkWeatherForecast.networkWeatherCondition,
                networkWeatherForecast.networkWeatherDescription,
                networkWeatherForecast.wind,
                networkWeatherForecast.date
            )
        }
    }

    override fun transformToDto(type: List<WeatherForecast>): List<NetworkWeatherForecast> {
        return type.map { weatherForecast ->
            NetworkWeatherForecast(
                weatherForecast.uID,
                weatherForecast.networkWeatherCondition,
                weatherForecast.networkWeatherDescription,
                weatherForecast.wind,
                weatherForecast.date
            )
        }
    }
}