package com.example.chcweather.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chcweather.data.model.NetworkWeatherCondition
import com.example.chcweather.data.model.NetworkWeatherDescription
import com.example.chcweather.data.model.Wind

@Entity(tableName = "weather_table")
data class DBWeather(

    @ColumnInfo(name = "unique_id")
    @PrimaryKey(autoGenerate = true)
    val uId: Int = 0,
    @ColumnInfo(name = "city_id")
    val cityId: Int,
    @ColumnInfo(name = "city_name")
    val cityName: String,
    @Embedded
    val wind: Wind,
    @ColumnInfo(name = "weather_details")
    val networkWeatherDescription: List<NetworkWeatherDescription>,
    @Embedded
    val networkWeatherCondition: NetworkWeatherCondition

)
