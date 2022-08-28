package com.example.chcweather.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chcweather.data.source.repository.WeatherRepository
import com.example.chcweather.ui.forecast.ForecastViewModel

class HomeViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ForecastViewModel::class.java)) {
            ForecastViewModel(repository) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}