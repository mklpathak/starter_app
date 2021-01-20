package com.example.samplemovieapp.dagger

import com.example.core.database.PopularDao
import com.example.core.network.ApiHelper
import com.example.samplemovieapp.MainRepository
import dagger.Module
import dagger.Provides
import io.plaidapp.core.dagger.scope.FeatureScope

@Module
class RepositoryModule {
    @FeatureScope
    @Provides
    fun provideRepository(apiHelper: ApiHelper, popularDao: PopularDao): MainRepository {
        return MainRepository(apiHelper,popularDao)
    }
}