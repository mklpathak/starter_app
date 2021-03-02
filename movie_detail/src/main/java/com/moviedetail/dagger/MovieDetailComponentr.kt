package com.samplemovieapp.dagger

import com.core.dagger.BaseFragmentComponent
import com.core.dagger.CoreComponent
import com.moviedetail.ui.home.MovieDetailFragment
import com.core.dagger.scope.FeatureScope
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [MovieDetailModule::class,MovieDetailRepositoryModule::class],
    dependencies = [CoreComponent::class]
)
@FeatureScope
interface MovieDetailComponentr : BaseFragmentComponent<MovieDetailFragment> {

    @Component.Builder
    interface Builder {

        fun build(): MovieDetailComponentr
        @BindsInstance fun homeFragment(fragmentComponent: MovieDetailFragment): Builder
        fun coreComponent(module: CoreComponent): Builder
    }
}
