package com.example.chcweather.ui.home

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.chcweather.R
import com.example.chcweather.data.source.local.WeatherDatabase
import com.example.chcweather.data.source.repository.WeatherRepository
import com.example.chcweather.databinding.FragmentHomeBinding
import com.example.chcweather.ui.BaseFragment
import com.example.chcweather.utils.GpsUtil
import com.example.chcweather.utils.SharedPreferenceHelper
import com.example.chcweather.utils.observeOnce
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var database: WeatherDatabase

    @Inject
    lateinit var weatherRepository: WeatherRepository

    @Inject
    lateinit var prefs: SharedPreferenceHelper

    private val uiScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.Main) }
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private var isGPSEnabled = false
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        {
            if (it.all { permission -> permission.value }) {
                isGPSEnabled = true
                invokeLocationAction()
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.enable_gps),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        private const val LOCATION_REQUEST_CODE = 123
        private const val TAG = "Home"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GpsUtil(requireContext()).turnGPSOn(object : GpsUtil.OnGpsListener {
            override fun gpsStatus(isGPSEnabled: Boolean) {
                this@HomeFragment.isGPSEnabled = isGPSEnabled
            }
        })
    }

    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this@HomeFragment,
            factory
        )[HomeViewModel::class.java]
        binding = FragmentHomeBinding.inflate(inflater).apply {
            vm = viewModel
            swipeRefreshId.setOnRefreshListener {
                errorText.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                hideView(true)
                initiateRefresh()
                swipeRefreshId.isRefreshing = false
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideAllView(true)
        observeViewModels()
    }

    private fun observeViewModels() {
        with(viewModel) {
            weather.observe(viewLifecycleOwner) { weather ->
                binding.let {
                    it.weather = weather
                    it.networkWeatherDescription =
                        weather?.networkWeatherDescription?.get(0)
                }
            }

            dataFetchState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    true -> {
                        hideView(false)
                        binding.errorText.visibility = View.GONE
                    }
                    false -> {
                        hideView(true)
                        binding.run {
                            errorText.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            loadingText.visibility = View.GONE
                        }
                    }
                }
            }

            isLoading.observe(viewLifecycleOwner) { state ->
                when (state) {
                    true -> {
                        hideView(true)
                        binding.run {
                            progressBar.visibility = View.VISIBLE
                            loadingText.visibility = View.VISIBLE
                        }
                    }
                    false -> {
                        binding.run {
                            progressBar.visibility = View.GONE
                            loadingText.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }


    private fun invokeLocationAction() {
        when {
            allPermissionsGranted() -> {
                viewModel.fetchLocationLiveData(context)?.observeOnce(
                    viewLifecycleOwner
                ) { location ->
                    location?.let {
                        viewModel.getWeather(it, uiScope)
                        setupWorkManager()
                    }
                }
                Log.d(TAG, "All Permissions Granted")
            }

            //사용자가 이전에 권한 요청을 거부한 경우 true를 반환
            shouldShowRequestPermissionRationale() -> {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.location_permission))
                    .setMessage(getString(R.string.access_location_message))
                    .setNegativeButton(getString(R.string.no)) { _, _ ->
                        requireActivity().finish()
                    }.setPositiveButton(getString(R.string.ask_me)) { _, _ ->
                        requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
                    }.show()
            }

            !isGPSEnabled -> {
                showShortSnackBar(getString(R.string.gps_required_message))
            }

            else -> {
                requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
                Log.d(TAG, "Permissions rejected")
            }
        }
    }

    private fun initiateRefresh() {
        viewModel.fetchLocationLiveData(context)?.observeOnce(
            viewLifecycleOwner
        ) { location ->
            prefs.saveLocation(location)
            viewModel.refreshWeather(location, uiScope)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        uiScope.cancel()
    }

    private fun hideAllView(hide: Boolean) {
        binding.run {
            weatherDet.visibility = if (hide) View.GONE else View.VISIBLE
            separator.visibility = if (hide) View.GONE else View.VISIBLE
            dateText.visibility = if (hide) View.GONE else View.VISIBLE
            weatherIcon.visibility = if (hide) View.GONE else View.VISIBLE
            weatherTemperature.visibility = if (hide) View.GONE else View.VISIBLE
            weatherMain.visibility = if (hide) View.GONE else View.VISIBLE
            errorText.visibility = if (hide) View.GONE else View.VISIBLE
            progressBar.visibility = if (hide) View.GONE else View.VISIBLE
            loadingText.visibility = if (hide) View.GONE else View.VISIBLE
        }
    }

    private fun hideView(hide: Boolean) {
        binding.run {
            weatherInText.visibility = if (hide) View.GONE else View.VISIBLE
            weatherDet.visibility = if (hide) View.GONE else View.VISIBLE
            separator.visibility = if (hide) View.GONE else View.VISIBLE
            dateText.visibility = if (hide) View.GONE else View.VISIBLE
            weatherIcon.visibility = if (hide) View.GONE else View.VISIBLE
            weatherTemperature.visibility = if (hide) View.GONE else View.VISIBLE
            weatherMain.visibility = if (hide) View.GONE else View.VISIBLE
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    //권한요청을 명시적으로 거부한 경우 true를 반환
    //권한요청을 처음 보거나, 다시 묻지 않음 선택한 경우, 권한을 허용한 경우 false를 반환
    private fun shouldShowRequestPermissionRationale() = REQUIRED_PERMISSIONS.all {
        shouldShowRequestPermissionRationale(it)
    }

    private fun setupWorkManager() {
        viewModel.run {
            fetchLocationLiveData(context)?.observeOnce(this@HomeFragment) {
                prefs.saveLocation(it)
            }
            setupWorkManager(requireContext())
        }
    }

}