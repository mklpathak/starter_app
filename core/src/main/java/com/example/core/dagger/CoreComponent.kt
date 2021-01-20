package com.example.core.dagger

import android.app.Application
import com.example.core.database.PopularDao
import com.example.core.network.ApiHelper
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [CoreDataModule::class])
@Singleton
interface CoreComponent {

    @Component.Builder interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): CoreComponent
    }

    fun provideApiHelper(): ApiHelper
    fun providePopularDao():PopularDao

}
