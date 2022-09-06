package com.example.chcweather.ui.forecast

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chcweather.data.model.LocationModel
import com.example.chcweather.data.model.WeatherForecast
import com.example.chcweather.data.source.repository.WeatherRepository
import com.example.chcweather.utils.LocationLiveData
import com.example.chcweather.utils.Result
import com.example.chcweather.utils.asLiveData
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
                        val forecasts = result.data
                        _dataFetchState.value = true
                        _forecast.value = forecasts
                    }
                }
                is Result.Loading -> _isLoading.postValue(true)
                else -> {}
            }
        }
    }
}