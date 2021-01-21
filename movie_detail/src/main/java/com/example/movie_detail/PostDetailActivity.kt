package com.example.movie_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.DeepLink

@DeepLink("detail://example.com/moviedetail/{id}")

class PostDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
    }
}