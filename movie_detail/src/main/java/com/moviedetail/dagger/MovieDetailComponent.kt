package com.moviedetail.dagger

import com.core.dagger.BaseFragmentComponent
import com.core.dagger.CoreComponent
import com.moviedetail.ui.home.MovieDetailFragment
import com.core.dagger.scope.FeatureScope
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [MovieDetailModule::class, MovieDetailRepositoryModule::class],
    dependencies = [CoreComponent::class]
)
@FeatureScope
interface MovieDetailComponent : BaseFragmentComponent<MovieDetailFragment> {

    @Component.Builder
    interface Builder {

        fun build(): MovieDetailComponent
        @BindsInstance fun fragment(fragmentComponent: MovieDetailFragment): Builder
        fun coreComponent(module: CoreComponent): Builder
    }
}
