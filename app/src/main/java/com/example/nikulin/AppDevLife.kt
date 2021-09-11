package com.example.nikulin

import android.app.Application
import android.content.Context
import com.example.nikulin.di.AppComponent
import com.example.nikulin.di.DaggerAppComponent

class AppDevLife : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        appComponent = DaggerAppComponent.create()
        super.onCreate()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is AppDevLife -> appComponent
        else -> this.applicationContext.appComponent
    }