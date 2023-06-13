package com.example.mathalarm.presentation.adapters

import android.app.Application
import com.example.mathalarm.di.DaggerApplicationComponent


class AlarmApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}