package com.example.chcweather.di.modules

import com.example.chcweather.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuilderModule {
    @ContributesAndroidInjector
    abstract fun getMainActivityModule(): MainActivity
}