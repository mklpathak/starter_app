package com.moviedetail.dagger
import com.moviedetail.ui.home.MovieDetailFragment
import com.samplemovieapp.dagger.startCoreComponent


fun MovieDetailFragment.inject() {
    this.startCoreComponent()?.let {
        DaggerMovieDetailComponent.builder()
            .coreComponent(it).fragment(this)
            .build()
            .inject(this)
    }
}

