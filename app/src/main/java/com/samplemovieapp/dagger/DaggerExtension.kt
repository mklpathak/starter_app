package com.samplemovieapp.dagger

import androidx.fragment.app.Fragment
import com.samplemovieapp.MovieApp
import com.samplemovieapp.dagger.DaggerHomeComponent
import com.samplemovieapp.ui.home.HomeFragment

fun Fragment.startCoreComponent() = this.context?.let { MovieApp.coreComponent(it) }

fun HomeFragment.inject() {
    this.startCoreComponent()?.let {
        DaggerHomeComponent.builder()
            .coreComponent(it).homeFragment(this)
            .build()
            .inject(this)
    }
}
