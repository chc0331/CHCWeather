package com.example.chcweather.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.chcweather.databinding.FragmentForecastBinding
import com.example.chcweather.ui.BaseFragment
import com.example.chcweather.ui.home.ViewModelFactory
import com.example.chcweather.utils.observeOnce
import javax.inject.Inject

class ForecastFragment : BaseFragment(), WeatherForecastAdapter.ForecastOnClickListener {

    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var binding: FragmentForecastBinding
    private lateinit var viewModel: ForecastViewModel
    private val weatherForecastAdapter by lazy { WeatherForecastAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this@ForecastFragment,
            factory
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
        binding.forecastRecyclerview.adapter = weatherForecastAdapter
        observeMoreViewModels()
        setupCalendar()
    }

    private fun observeMoreViewModels() {
        with(viewModel) {

            viewModel.fetchLocationLiveData(context)?.observeOnce(
                viewLifecycleOwner
            ) { location ->
                location?.let { viewModel.getWeatherForecast(it) }
            }

            forecast.observe(viewLifecycleOwner) {
                weatherForecastAdapter.submitList(it)
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
            initiateRefresh()
        }
    }

    private fun initiateRefresh() {
        binding.run {
            forecastErrorText.visibility = View.GONE
            forecastProgressBar.visibility = View.VISIBLE
            forecastRecyclerview.visibility = View.GONE
            forecastSwipeRefresh.isRefreshing = false
            viewModel.fetchLocationLiveData(context)?.observeOnce(
                viewLifecycleOwner
            ) { location ->
                location?.let { viewModel.refreshForecastData(it) }
            }
        }

    }

    private fun setupCalendar() {
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            binding.emptyListText.visibility = View.GONE
        }
    }
}