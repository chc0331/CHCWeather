package com.example.chcweather.di.modules

import android.content.Context
import com.example.chcweather.utils.SharedPreferenceHelper
import dagger.Module
import dagger.Provides

@Module
class SharedPrefsModule {

    @Provides
    fun provideSharedPref(context: Context): SharedPreferenceHelper {
        return SharedPreferenceHelper.getInstance(context)
    }

}