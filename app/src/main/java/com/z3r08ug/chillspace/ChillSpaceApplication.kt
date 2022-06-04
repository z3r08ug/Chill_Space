package com.z3r08ug.chillspace

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Configuration
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.z3r08ug.chillspace.ui.shared.LifecycleMonitor
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class ChillSpaceApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
//    @Inject
//    lateinit var lifecycleMonitor: LifecycleMonitor

    override fun onCreate() {
        super.onCreate()

//        setupLogging()
//        setupAnalytics()
//        setupCrashlytics()
//        setupTheme()

//        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleMonitor)
    }

//    private fun setupLogging() {
//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        }
//    }

//    private fun setupAnalytics() {
//        // TODO: setup a production (& beta) proxy
//        if (BuildConfig.DEBUG) {
//            Analytics.register(LoggingAnalyticsProxy())
//        }
//    }

//    private fun setupCrashlytics() {
        // NOTE:
        // Crashlytics isn't setup for tests so calling getInstance() will throw an exception.
        // Until we can find a cleaner solution we just silently consume the error
//        try  {
//            // Only enable Crashlytics on Release builds
//            val isReleaseBuild = BuildConfig.BUILD_TYPE.equals("release", true)
//            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(isReleaseBuild)
//        } catch(e: IllegalStateException) {
//            // Purposefully left blank
//        }
//    }

//    private fun setupTheme() {
//        theme.observe(ProcessLifecycleOwner.get()) {
//            val mode = when(it) {
//                Theme.LIGHT -> MODE_NIGHT_NO
//                Theme.DARK -> MODE_NIGHT_YES
//                else -> MODE_NIGHT_FOLLOW_SYSTEM
//            }
//
//            setDefaultNightMode(mode)
//        }
//    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()
}