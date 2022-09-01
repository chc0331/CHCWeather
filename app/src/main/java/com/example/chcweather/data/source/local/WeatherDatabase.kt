package com.example.chcweather.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chcweather.data.source.local.dao.WeatherDao
import com.example.chcweather.data.source.local.entity.DBWeather
import com.example.chcweather.data.source.typeconverts.ListNetworkWeatherDescriptionConverter

@Database(entities = [DBWeather::class], version = 1, exportSchema = true)
@TypeConverters(
    ListNetworkWeatherDescriptionConverter::class
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val weatherDao: WeatherDao

    //Singleton
    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}