package com.example.chcweather.di.modules

import com.example.chcweather.data.source.remote.retrofit.WeatherApiService
import com.example.chcweather.data.source.remote.retrofit.WeatherService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideApiService(): WeatherApiService =
        WeatherService.service
}