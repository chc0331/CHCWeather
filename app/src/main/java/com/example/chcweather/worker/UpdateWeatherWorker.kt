package com.example.chcweather.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.chcweather.data.source.repository.WeatherRepository
import com.example.chcweather.utils.NotificationHelper
import com.example.chcweather.utils.Result
import com.example.chcweather.utils.Result.Success
import com.example.chcweather.utils.SharedPreferenceHelper

class UpdateWeatherWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: WeatherRepository
) : CoroutineWorker(context, params) {

    private val notificationHelper = NotificationHelper("Weather Update", context)
    private val sharedPrefs = SharedPreferenceHelper.getInstance(context)

    override suspend fun doWork(): Result {
        val location = sharedPrefs.getLocation()
        return when (val result = repository.getWeather(location, true)) {
            is Success -> {
                if (result.data != null) {
                    when (val foreResult = repository.getForecastWeather(location, true)) {
                        is Success -> {
                            if (foreResult.data != null) {
                                notificationHelper.createNotification()
                                Result.success()
                            } else
                                Result.failure()
                        }
                        else -> Result.failure()
                    }
                } else {
                    Result.failure()
                }
            }
            else -> Result.failure()
        }
    }
}