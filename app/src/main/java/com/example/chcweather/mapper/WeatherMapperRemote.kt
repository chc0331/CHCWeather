package com.example.chcweather.mapper

import com.example.chcweather.data.model.NetworkWeather
import com.example.chcweather.data.model.Weather

class WeatherMapperRemote : BaseMapper<NetworkWeather, Weather> {
    override fun transformToDomain(type: NetworkWeather): Weather = Weather(
        type.uId,
        type.cityId,
        type.name,
        type.wind,
        type.networkWeatherDescriptions,
        type.networkWeatherCondition
    )

    override fun transformToDto(type: Weather): NetworkWeather = NetworkWeather(
        type.uId,
        type.cityId,
        type.name,
        type.wind,
        type.networkWeatherDescription,
        type.networkWeatherCondition
    )
}