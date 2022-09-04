package com.example.chcweather.di.modules

import com.example.chcweather.data.source.local.WeatherLocalDataSource
import com.example.chcweather.data.source.local.WeatherLocalDataSourceImpl
import com.example.chcweather.data.source.local.dao.WeatherDao
import com.example.chcweather.data.source.remote.WeatherRemoteDataSource
import com.example.chcweather.data.source.remote.WeatherRemoteDataSourceImpl
import com.example.chcweather.data.source.remote.retrofit.WeatherApiService
import com.example.chcweather.data.source.repository.WeatherRepository
import com.example.chcweather.data.source.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun providesRepository(
        remoteDataSource: WeatherRemoteDataSource,
        localDatasource: WeatherLocalDataSource
    ): WeatherRepository = WeatherRepositoryImpl(remoteDataSource, localDatasource)

    @Singleton
    @Provides
    fun provideLocalDataSource(dao: WeatherDao): WeatherLocalDataSource =
        WeatherLocalDataSourceImpl(dao)

    @Singleton
    @Provides
    fun provideRemoteDataSource(service: WeatherApiService): WeatherRemoteDataSource =
        WeatherRemoteDataSourceImpl(service)
}