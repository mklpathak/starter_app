package com.samplemovieapp.dagger

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.samplemovieapp.HomeActivity
import com.samplemovieapp.ui.home.HomeFragment
import com.samplemovieapp.ui.home.HomeViewModel
import com.samplemovieapp.ui.home.HomeViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class HomeModule {

    @Binds
    abstract fun homeActivityAsFragmentActivity(activity: HomeActivity): FragmentActivity

    @Binds
    abstract fun homeActivityAsActivity(activity: HomeActivity): Activity


    @Binds
    abstract fun homeFragment(fragment: HomeFragment): Fragment

    @Binds
    abstract fun context(activity: Activity): Context


    @Module
    companion object {
        @JvmStatic
        @Provides
        fun homeViewModel(
            factory: HomeViewModelFactory,
            fragment: Fragment
        ): HomeViewModel {
            return ViewModelProvider(fragment, factory).get(HomeViewModel::class.java)
        }
    }
}
