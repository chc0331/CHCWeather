package com.example.chcweather.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chcweather.data.model.Weather
import com.example.chcweather.data.source.repository.WeatherRepository
import com.example.chcweather.utils.Result
import com.example.chcweather.utils.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    private val _weatherInfo = MutableLiveData<Weather?>()
    val weatherInfo = _weatherInfo.asLiveData()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading.asLiveData()

    private val _dataFetchState = MutableLiveData<Boolean>()
    val dataFetchState = _dataFetchState.asLiveData()

    fun getSearchWeather(name: String, uiScope: CoroutineScope) {
        _isLoading.postValue(true)
        uiScope.launch {
            when (val result = repository.getSearchWeather(name)) {
                is Result.Success -> {
                    _isLoading.postValue(false)
                    if (result.data != null) {
                        _dataFetchState.postValue(true)
                        _weatherInfo.postValue(result.data)
                    } else {
                        _weatherInfo.postValue(null)
                        _dataFetchState.postValue(false)
                    }
                }
                is Result.Error -> {
                    _isLoading.postValue(false)
                    _dataFetchState.postValue(false)
                }
                else -> {}
            }
        }
    }

}