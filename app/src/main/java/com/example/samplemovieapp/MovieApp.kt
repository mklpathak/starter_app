package com.example.samplemovieapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}