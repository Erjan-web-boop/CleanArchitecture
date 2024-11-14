package com.example.cleanarchitecture

import android.app.Application
import com.example.cleanarchitecture.data.di.DataModule
import com.example.cleanarchitecture.domain.di.DomainModule
import com.example.cleanarchitecture.presentation.module.iuModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(DataModule)
            modules(DomainModule)
            modules(iuModule)
        }

    }
}