package com.example.samplemovieapp.dagger

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.core.database.PopularDao
import com.example.core.network.ApiHelper
import com.example.samplemovieapp.HomeActivity
import com.example.samplemovieapp.MainRepository
import com.example.samplemovieapp.ui.home.HomeFragment
import com.example.samplemovieapp.ui.home.HomeViewModel
import com.example.samplemovieapp.ui.home.HomeViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.plaidapp.core.dagger.scope.FeatureScope

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
