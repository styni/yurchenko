package com.example.developerslife.di

import android.app.Application
import com.example.developerslife.di.components.AppComponent
import com.example.developerslife.di.components.DaggerAppComponent
import com.example.developerslife.di.modules.AppModule


class App : Application() {
    var appComponent: AppComponent? = null
        get() {
            if (field == null) {
                field = DaggerAppComponent.builder()
                    .appModule(AppModule(this))
                    .build()
            }
            return field
        }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
    }
}