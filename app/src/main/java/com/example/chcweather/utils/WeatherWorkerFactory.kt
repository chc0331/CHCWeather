package com.example.chcweather.utils

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.chcweather.data.source.repository.WeatherRepository
import com.example.chcweather.worker.UpdateWeatherWorker

class WeatherWorkerFactory(private val repository: WeatherRepository) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            UpdateWeatherWorker::class.java.name -> {
                UpdateWeatherWorker(appContext, workerParameters, repository)
            }
            else -> null
        }
    }
}