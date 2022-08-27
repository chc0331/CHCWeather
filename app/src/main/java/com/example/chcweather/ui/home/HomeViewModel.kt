package com.example.chcweather.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chcweather.data.model.LocationModel
import com.example.chcweather.data.model.Weather
import com.example.chcweather.data.source.repository.WeatherRepository
import com.example.chcweather.utils.LocationLiveData
import com.example.chcweather.utils.asLiveData
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(private val repository: WeatherRepository) : ViewModel() {

    val time = currentSystemTime()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading.asLiveData()

    private val _dataFetchState = MutableLiveData<Boolean>()
    val dataFetchState = _dataFetchState.asLiveData()

    private val _weather = MutableLiveData<Weather>()
    val weather = _weather.asLiveData()

    fun fetchLocationLiveData(context: Context?) = context?.let { LocationLiveData(it) }

    fun currentSystemTime(): String {
        val currentTime = System.currentTimeMillis()
        val date = Date(currentTime)
        val dateFormat = SimpleDateFormat("EEEE MMM d, hh:mm aaa")
        return dateFormat.format(date)
    }

    /*
    * This attemps to get the Weather from the local data source
    * if the result is null, it gets from the remote source
    * */
    fun getWeather(location: LocationModel) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            val result = repository.getWeather()
            _isLoading.postValue(false)
            _dataFetchState.value = true
            _weather.value = result
        }

    }

    /*
    * This is called when the user swipes down to refresh
    * */
    fun refreshWeather(location: LocationModel) {

    }

}