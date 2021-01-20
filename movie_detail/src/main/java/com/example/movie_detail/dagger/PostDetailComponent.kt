package com.example.samplemovieapp.dagger

import com.example.core.dagger.BaseFragmentComponent
import com.example.core.dagger.CoreComponent
import com.example.movie_detail.PostDetailFragment
import dagger.BindsInstance
import dagger.Component
import io.plaidapp.core.dagger.scope.FeatureScope

@Component(
    modules = [PostDetailModule::class,PostDetailRepositoryModule::class],
    dependencies = [CoreComponent::class]
)
@FeatureScope
interface PostDetailComponent : BaseFragmentComponent<PostDetailFragment> {

    @Component.Builder
    interface Builder {

        fun build(): PostDetailComponent
        @BindsInstance fun homeFragment(fragmentComponent: PostDetailFragment): Builder
        fun coreComponent(module: CoreComponent): Builder
    }
}
