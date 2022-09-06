package com.example.chcweather.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chcweather.data.model.NetworkWeatherCondition
import com.example.chcweather.data.model.NetworkWeatherDescription
import com.example.chcweather.data.model.Wind

@Entity(tableName = "weather_forecast")
data class DBWeatherForecast(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    @Embedded
    val wind: Wind,
    @ColumnInfo(name = "weather_description")
    val networkWeatherDescriptions: List<NetworkWeatherDescription>,
    @Embedded
    val networkWeatherCondition: NetworkWeatherCondition
)
