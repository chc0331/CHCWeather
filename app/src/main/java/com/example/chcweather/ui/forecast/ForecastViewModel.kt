package com.example.chcweather.ui.forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chcweather.data.model.WeatherForecast
import com.example.chcweather.data.source.repository.WeatherRepository
import com.example.chcweather.utils.asLiveData
import kotlinx.coroutines.launch

class ForecastViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _forecast = MutableLiveData<List<WeatherForecast>?>()
    val forecast = _forecast.asLiveData()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading.asLiveData()

    private val _dataFetchState = MutableLiveData<Boolean>()
    val dataFetchState = _dataFetchState.asLiveData()

    private val _filteredForecast = MutableLiveData<List<WeatherForecast>>()
    val filteredForecast = _filteredForecast.asLiveData()

    fun getWeatherForecast(cityId: Int?) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            val result = repository.getForecastWeather()
            _isLoading.postValue(false)
            _dataFetchState.value = true
            _forecast.value = result
        }
    }
}