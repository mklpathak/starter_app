package com.example.samplemovieapp.deeplink

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.DeepLinkHandler
import com.example.samplemovieapp.deeplink.AppDeepLinkModule
import com.example.samplemovieapp.deeplink.AppDeepLinkModuleRegistry
import com.example.samplemovieapp.deeplink.DeepLinkDelegate


@DeepLinkHandler(AppDeepLinkModule::class)
class DeepLinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val deepLinkDelegate =
            DeepLinkDelegate(
                AppDeepLinkModuleRegistry()
            )
           deepLinkDelegate.dispatchFrom(this)
        finish()
    }
}