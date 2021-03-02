package com.samplemovieapp.dagger

import com.core.dagger.BaseFragmentComponent
import com.core.dagger.CoreComponent
import com.core.dagger.scope.FeatureScope
import com.samplemovieapp.ui.home.HomeFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [HomeModule::class,RepositoryModule::class],
    dependencies = [CoreComponent::class]
)
@FeatureScope
interface HomeComponent : BaseFragmentComponent<HomeFragment> {

    @Component.Builder
    interface Builder {

        fun build(): HomeComponent
        @BindsInstance fun homeFragment(fragmentComponent: HomeFragment): Builder
        fun coreComponent(module: CoreComponent): Builder
    }
}
