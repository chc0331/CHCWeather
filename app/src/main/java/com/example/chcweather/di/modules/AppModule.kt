package com.example.chcweather.di.modules

import dagger.Module
import dagger.android.AndroidInjectionModule

@Module(
    includes = [AndroidInjectionModule::class,
        ActivitiesBuilderModule::class,
        FragmentsBuilderModule::class,
        ViewModelModule::class,
        DatabaseModule::class,
        RetrofitModule::class,
        RepositoryModule::class]
)
class AppModule {
}