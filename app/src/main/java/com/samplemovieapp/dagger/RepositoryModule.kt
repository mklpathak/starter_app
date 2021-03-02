package com.samplemovieapp.dagger

import com.core.dagger.scope.FeatureScope
import com.core.database.PopularDao
import com.core.network.ApiHelper
import com.samplemovieapp.MainRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @FeatureScope
    @Provides
    fun provideRepository(apiHelper: ApiHelper, popularDao: PopularDao): MainRepository {
        return MainRepository(apiHelper,popularDao)
    }
}