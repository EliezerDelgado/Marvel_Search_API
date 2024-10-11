package com.eliezer.marvel_search_api

import android.app.Application
import com.eliezer.marvel_search_api.domain.local_property.LocalDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MarvelSearchAPIApplication : Application()  {
    override fun onCreate() {
        super.onCreate()
        LocalDatabase.setLocalDatabase(this)
    }
}