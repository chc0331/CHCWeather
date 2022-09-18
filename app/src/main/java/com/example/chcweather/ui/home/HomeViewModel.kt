package com.example.chcweather.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.chcweather.data.model.LocationModel
import com.example.chcweather.data.model.Weather
import com.example.chcweather.data.source.repository.WeatherRepository
import com.example.chcweather.utils.LocationLiveData
import com.example.chcweather.utils.Result
import com.example.chcweather.utils.asLiveData
import com.example.chcweather.utils.convertKelvinToCelsius
import com.example.chcweather.worker.UpdateWeatherWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    val time = currentSystemTime()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading.asLiveData()

    private val _dataFetchState = MutableLiveData<Boolean>()
    val dataFetchState = _dataFetchState.asLiveData()

    private val _weather = MutableLiveData<Weather?>()
    val weather = _weather.asLiveData()

    fun fetchLocationLiveData(context: Context?) = context?.let { LocationLiveData(it) }

    private fun currentSystemTime(): String {
        val currentTime = System.currentTimeMillis()
        val date = Date(currentTime)
        val dateFormat = SimpleDateFormat("EEEE MMM d, hh:mm aaa")
        return dateFormat.format(date)
    }

    /*
    * This attempts to get the Weather from the local data source
    * if the result is null, it gets from the remote source
    * */
    fun getWeather(location: LocationModel, uiScope: CoroutineScope) {
        _isLoading.postValue(true)
        uiScope.launch {
            when (val result = repository.getWeather(location, false)) {
                is Result.Success -> {
                    _isLoading.postValue(false)
                    if (result.data != null) {
                        val weather = result.data
                        _dataFetchState.value = true
                        _weather.value = weather
                    } else {
                        refreshWeather(location, uiScope)
                    }
                }
                is Result.Error -> {
                    _isLoading.value = false
                    _dataFetchState.value = false
                }
                is Result.Loading -> _isLoading.postValue(true)
            }
        }
    }

    /*
    * This is called when the user swipes down to refresh
    * */
    fun refreshWeather(location: LocationModel, uiScope: CoroutineScope) {
        _isLoading.postValue(true)
        uiScope.launch {
            when (val result = repository.getWeather(location, true)) {
                is Result.Success -> {
                    _isLoading.postValue(false)
                    if (result.data != null) {
                        val weather = result.data.apply {
                            networkWeatherCondition.temp =
                                convertKelvinToCelsius(networkWeatherCondition.temp)
                        }
                        _dataFetchState.value = true
                        _weather.value = weather

                        repository.deleteWeatherData()
                        repository.storeWeatherData(weather)
                    } else {
                        _weather.postValue(null)
                        _dataFetchState.postValue(false)
                    }
                }

                is Result.Error -> {
                    _isLoading.postValue(false)
                    _dataFetchState.value = false
                }

                is Result.Loading -> _isLoading.postValue(true)
            }
        }
    }

    fun setupWorkManager(context: Context) {
        val workManager = WorkManager.getInstance(context)
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val weatherUpdateRequest =
            PeriodicWorkRequestBuilder<UpdateWeatherWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraint)
//                .setInitialDelay(6, TimeUnit.HOURS)
                .build()
        workManager.cancelAllWork()
        workManager.enqueueUniquePeriodicWork(
            "Update_weather_worker",
            ExistingPeriodicWorkPolicy.REPLACE, weatherUpdateRequest
        )
    }
}