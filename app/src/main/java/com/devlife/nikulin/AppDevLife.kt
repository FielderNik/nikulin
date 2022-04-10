package com.devlife.nikulin

import android.app.Application
import android.content.Context
import com.devlife.nikulin.presentation.di.AppComponent


class AppDevLife : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        appComponent = com.devlife.nikulin.presentation.di.DaggerAppComponent.create()
        super.onCreate()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is AppDevLife -> appComponent
        else -> this.applicationContext.appComponent
    }