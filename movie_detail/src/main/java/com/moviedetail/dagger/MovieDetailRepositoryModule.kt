package com.moviedetail.dagger

import com.core.database.PopularDao
import com.core.network.ApiHelper
import com.moviedetail.MovieDetailRepository
import com.core.dagger.scope.FeatureScope
import dagger.Module
import dagger.Provides

@Module
class MovieDetailRepositoryModule {
    @FeatureScope
    @Provides
    fun provideRepository(apiHelper: ApiHelper, popularDao: PopularDao): MovieDetailRepository {
        return MovieDetailRepository(apiHelper,popularDao)
    }
}