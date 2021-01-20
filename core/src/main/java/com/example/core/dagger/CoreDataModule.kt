package com.example.core.dagger

import android.app.Application
import androidx.room.Room
import com.example.core.BuildConfig
import com.example.core.Constants
import com.example.core.database.AppDatabase
import com.example.core.database.PopularDao
import com.example.core.network.ApiHelper
import com.example.core.network.ApiHelperImpl
import com.example.core.network.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Module
class CoreDataModule{

    @Provides
    @Named("BASE_URL")
    fun provideBaseUrl() = Constants.BASE_URL


    @Provides
    @Named("API_KEY")
    fun provideApiKey() = Constants.API_KEY

    @Provides
    @Named("LANGUAGE")
    fun provideLanguage() = Constants.LANGUAGE



    @Inject
    lateinit var application: Application


    @Provides
    fun provideInterceptor(@Named("API_KEY")API_KEY:String,@Named("LANGUAGE")LANGUAGE:String) = Interceptor{val original: Request = it.request()
        val originalHttpUrl: HttpUrl = original.url()
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("language",LANGUAGE)
            .build()
        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)
        val request: Request = requestBuilder.build()
        it.proceed(request)}




    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor) = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }else{
        OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, @Named("BASE_URL")BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Provides
    fun providePopulerDao(appDatabase: AppDatabase): PopularDao {
        return appDatabase.popularDao()
    }


    @Provides
    @Singleton
    fun provideAppDatabase( appContext: Application): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "TMDB"
        ).build()
    }

}