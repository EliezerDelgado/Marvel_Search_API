package com.eliezer.marvel_characters

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MarvelCharactersApplication : Application(), Configuration.Provider  {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    private var _workManagerConfiguration = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()

    override val workManagerConfiguration: Configuration
        get() = _workManagerConfiguration

}