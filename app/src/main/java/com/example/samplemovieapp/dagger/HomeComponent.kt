package com.example.samplemovieapp.dagger

import com.example.core.dagger.BaseFragmentComponent
import com.example.core.dagger.CoreComponent
import com.example.samplemovieapp.ui.home.HomeFragment
import dagger.BindsInstance
import dagger.Component
import io.plaidapp.core.dagger.scope.FeatureScope

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
