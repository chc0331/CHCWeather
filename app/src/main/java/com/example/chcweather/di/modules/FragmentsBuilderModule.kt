package com.example.chcweather.di.modules

import com.example.chcweather.ui.forecast.ForecastFragment
import com.example.chcweather.ui.home.HomeFragment
import com.example.chcweather.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeForecastFragment(): ForecastFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}