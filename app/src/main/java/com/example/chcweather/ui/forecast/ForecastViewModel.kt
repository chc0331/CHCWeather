package com.example.chcweather.ui.forecast

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chcweather.data.model.LocationModel
import com.example.chcweather.data.model.WeatherForecast
import com.example.chcweather.data.source.repository.WeatherRepository
import com.example.chcweather.utils.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForecastViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _forecast = MutableLiveData<List<WeatherForecast>?>()
    val forecast = _forecast.asLiveData()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading.asLiveData()

    private val _dataFetchState = MutableLiveData<Boolean>()
    val dataFetchState = _dataFetchState.asLiveData()

    private val _filteredForecast = MutableLiveData<List<WeatherForecast>>()
    val filteredForecast = _filteredForecast.asLiveData()

    fun fetchLocationLiveData(context: Context?) = context?.let { LocationLiveData(it) }

    fun getWeatherForecast(location: LocationModel) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            when (val result = repository.getForecastWeather(location, true)) {
                is Result.Success -> {
                    _isLoading.postValue(false)
                    if (!result.data.isNullOrEmpty()) {
                        val forecasts = result.data.onEach { forecast ->
                            forecast.networkWeatherCondition.temp =
                                convertKelvinToCelsius(forecast.networkWeatherCondition.temp)
                            forecast.date = forecast.date.formatDate()
                        }
                        _dataFetchState.value = true
                        _forecast.value = forecasts
                    }
                }
                is Result.Loading -> _isLoading.postValue(true)
                else -> {}
            }
        }
    }

    fun refreshForecastData(location: LocationModel) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            when (val result = repository.getForecastWeather(location, true)) {
                is Result.Success -> {
                    _isLoading.postValue(false)
                    if (result.data != null) {
                        val forecast = result.data.onEach { forecast ->
                            forecast.networkWeatherCondition.temp =
                                convertKelvinToCelsius(forecast.networkWeatherCondition.temp)
                            forecast.date = forecast.date.formatDate()
                        }
                        _forecast.postValue(forecast)
                        _dataFetchState.postValue(true)
                    } else {
                        _dataFetchState.postValue(false)
                        _forecast.postValue(null)
                    }
                }

                is Result.Error -> {
                    _dataFetchState.value = false
                    _isLoading.postValue(false)
                }

                is Result.Loading -> _isLoading.postValue(true)
            }
        }
    }
}