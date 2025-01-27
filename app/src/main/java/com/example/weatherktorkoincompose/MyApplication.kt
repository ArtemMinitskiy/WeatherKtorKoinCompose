package com.example.weatherktorkoincompose

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin with the app module
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}