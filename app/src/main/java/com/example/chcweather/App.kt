package com.example.chcweather

import android.app.Application
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import com.example.chcweather.data.source.repository.WeatherRepository
import com.example.chcweather.di.components.DaggerAppComponent
import com.example.chcweather.utils.WeatherWorkerFactory
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector, Configuration.Provider {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var weatherRepository: WeatherRepository

    override fun androidInjector() = androidInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .context(this)
            .build().inject(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        val workerFactory = DelegatingWorkerFactory()
        workerFactory.addFactory(WeatherWorkerFactory(weatherRepository))

        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}