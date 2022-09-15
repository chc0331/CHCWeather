package com.example.chcweather.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.example.chcweather.data.model.LocationModel
import com.google.gson.Gson

class SharedPreferenceHelper {

    companion object {
        private var prefs: SharedPreferences? = null
        private const val LOCATION = "LOCATION"

        @Volatile
        private var INSTANCE: SharedPreferenceHelper? = null

        fun getInstance(context: Context): SharedPreferenceHelper {
            return INSTANCE ?: synchronized(this) {
                prefs = PreferenceManager.getDefaultSharedPreferences(context)
                val instance = SharedPreferenceHelper()
                INSTANCE = instance
                instance
            }
        }
    }

    fun saveLocation(location: LocationModel) {
        prefs?.edit(commit = true) {
            val gson = Gson()
            val json = gson.toJson(location)
            putString(LOCATION, json)
        }
    }

    fun getLocation(): LocationModel {
        val gson = Gson()
        val json = prefs?.getString(LOCATION, null)
        return gson.fromJson(json, LocationModel::class.java)
    }
}