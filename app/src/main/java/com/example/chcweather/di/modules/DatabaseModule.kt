package com.example.chcweather.di.modules

import android.content.Context
import com.example.chcweather.data.source.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideWeatherDatabase(context: Context) =
        WeatherDatabase.getInstance(context)


    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase) =
        weatherDatabase.weatherDao

}