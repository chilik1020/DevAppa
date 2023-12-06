package com.chilik1020.devappa

import android.app.Application
import com.chilik1020.devappa.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DevAppa : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@DevAppa)
            modules(appModule)
        }
    }
}