package com.moviedetail.dagger

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.moviedetail.MovieDetailActivity
import com.moviedetail.ui.home.MovieDetailFragment
import com.moviedetail.ui.home.MovieDetailViewModel
import com.moviedetail.ui.home.MovieDetailViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MovieDetailModule {



    @Binds
    abstract fun homeActivityAsActivity(activity: MovieDetailActivity): Activity


    @Binds
    abstract fun homeFragment(fragment: MovieDetailFragment): Fragment

    @Binds
    abstract fun context(activity: Activity): Context


    @Module
    companion object {
        @JvmStatic
        @Provides
        fun homeViewModel(
            factory: MovieDetailViewModelFactory,
            fragment: Fragment
        ): MovieDetailViewModel {
            return ViewModelProvider(fragment, factory).get(MovieDetailViewModel::class.java)
        }
    }
}
