package com.example.chcweather.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.chcweather.data.source.local.WeatherDatabase
import com.example.chcweather.data.source.local.WeatherLocalDataSourceImpl
import com.example.chcweather.data.source.remote.WeatherRemoteDataSourceImpl
import com.example.chcweather.data.source.remote.retrofit.WeatherService
import com.example.chcweather.data.source.repository.WeatherRepositoryImpl
import com.example.chcweather.databinding.FragmentForecastBinding
import com.example.chcweather.ui.BaseFragment
import com.example.chcweather.ui.home.HomeViewModelFactory

class ForecastFragment : BaseFragment(), WeatherForecastAdapter.ForecastOnClickListener {
    private lateinit var binding: FragmentForecastBinding
    private lateinit var viewModel: ForecastViewModel
    private lateinit var db: WeatherDatabase
    private val weatherForecastAdapter by lazy { WeatherForecastAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = WeatherDatabase.getInstance(requireContext().applicationContext)!!
        viewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(
                WeatherRepositoryImpl
                    (
                    WeatherRemoteDataSourceImpl(WeatherService.service),
                    WeatherLocalDataSourceImpl(db.weatherDao)
                )
            )
        )[ForecastViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForecastBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCalendar()
        binding.forecastRecyclerview.adapter = weatherForecastAdapter
        viewModel.getWeatherForecast(0)
        observeMoreViewModels()
    }

    private fun observeMoreViewModels() {
        with(viewModel) {
            forecast.observe(viewLifecycleOwner) { weatherForeast ->
                weatherForeast?.let {
                    weatherForecastAdapter.submitList(it)
                }
            }

            dataFetchState.observe(viewLifecycleOwner) { state ->
                binding.apply {
                    forecastRecyclerview.isVisible = state
                    forecastErrorText.isVisible = !state
                }
            }

            isLoading.observe(viewLifecycleOwner) { state ->
                binding.forecastProgressBar.isVisible = state
            }

            filteredForecast.observe(viewLifecycleOwner) {
                weatherForecastAdapter.submitList(it)
                binding.emptyListText.isVisible = it.isEmpty()
            }
        }
        binding.forecastSwipeRefresh.setOnRefreshListener {
//            initiateRefresh()
        }

    }

    private fun setupCalendar() {
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            binding.emptyListText.visibility = View.GONE
        }
    }
}