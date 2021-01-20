package com.example.samplemovieapp.dagger

import com.example.core.database.PopularDao
import com.example.core.network.ApiHelper
import com.example.movie_detail.PostDetailRepository
import com.example.samplemovieapp.MainRepository
import dagger.Module
import dagger.Provides
import io.plaidapp.core.dagger.scope.FeatureScope

@Module
class PostDetailRepositoryModule {
    @FeatureScope
    @Provides
    fun provideRepository(apiHelper: ApiHelper, popularDao: PopularDao): PostDetailRepository {
        return PostDetailRepository(apiHelper,popularDao)
    }
}