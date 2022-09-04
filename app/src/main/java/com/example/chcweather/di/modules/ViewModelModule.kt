package com.example.chcweather.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chcweather.ui.home.HomeViewModel
import com.example.chcweather.utils.ViewModelFactory
import com.example.chcweather.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindSplashViewModel(viewModel: HomeViewModel): ViewModel
}