package com.example.samplemovieapp

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.core.dagger.CoreComponent
import com.example.core.dagger.DaggerCoreComponent
import com.example.samplemovieapp.dagger.DaggerHomeComponent
import com.google.android.play.core.splitcompat.SplitCompat

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.builder().application(this).build()
    }

    companion object {
        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as MovieApp).coreComponent
    }
}

fun Fragment.coreComponent() = this.context?.let { MovieApp.coreComponent(it) }
