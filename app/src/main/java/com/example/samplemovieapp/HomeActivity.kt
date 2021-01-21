package com.example.samplemovieapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.deeplinkdispatch.DeepLink
import com.google.android.material.bottomnavigation.BottomNavigationView


@DeepLink("app://example.com/deepLink/{id}")
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val intent = intent
        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
            val parameters = intent.extras
            val idString = parameters!!.getString("id")
            Log.e("mukuldeeplink",idString.toString())
        }
    }
}