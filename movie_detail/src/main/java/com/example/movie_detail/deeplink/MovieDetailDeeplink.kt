package com.example.movie_detail.deeplink

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.DeepLinkHandler
import com.example.samplemovieapp.deeplink.AppDeepLinkModule
import com.example.samplemovieapp.deeplink.AppDeepLinkModuleRegistry
import com.example.samplemovieapp.deeplink.DeepLinkDelegate


@DeepLinkHandler(PostDetailDeepLinkModule::class)
class MovieDetailDeeplink : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val deepLinkDelegate =
            DeepLinkDelegate(
                PostDetailDeepLinkModuleRegistry()
            )
           deepLinkDelegate.dispatchFrom(this)
        finish()
    }
}