package com.nsicyber.classifyimages

import android.app.Application
import android.content.Context
import com.nsicyber.classifyimages.interfaces.ContextProvider
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ClassifyImagesApplication : Application(), ContextProvider {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
    override fun getContext(): Context {
        return this
    }
}