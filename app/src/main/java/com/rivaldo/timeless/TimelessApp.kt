package com.rivaldo.timeless

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Base Application class for Timeless.
 * Initializes Hilt dependency injection and app-wide configurations.
 */
@HiltAndroidApp
class TimelessApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Future: Initialize WorkManager, LeakCanary, or other app-wide services here
    }
}
