package com.example.samplemovieapp.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.carousel
import com.example.core.models.Header
import com.example.core.models.Popular
import com.example.core.utils.Status
import com.example.core.utils.withModelsFrom
import com.example.samplemovieapp.BuildConfig
import com.example.samplemovieapp.PopularMovieBindingModel_
import com.example.samplemovieapp.dagger.inject
import com.example.samplemovieapp.databinding.FragmentHomeBinding
import com.example.samplemovieapp.header
import com.example.samplemovieapp.popularMovie
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import javax.inject.Inject


class HomeFragment : Fragment(), LifecycleOwner {

    private var splitInstallManager: SplitInstallManager? = null

    @Inject
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        inject(this)
        var binding = FragmentHomeBinding.inflate(LayoutInflater.from(context))
        binding.movies.setLayoutManager(GridLayoutManager(context, 3))

        installRegistrationModule()

        


        homeViewModel.res.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {

                    binding.movies.withModels {
                            it.data?.forEach {
                                when(it){
                                    is Header-> header {
                                        id("popular_movies_title")
                                        title(it.title)
                                        spanSizeOverride { _, _, _ -> 3 }
                                    }
                                    is Popular ->carousel {
                                        id("carousel")
                                        withModelsFrom(it.results) { movie ->
                                            PopularMovieBindingModel_().id(movie.id).movie(movie).onBind { model, view, position ->

                                                view.dataBinding.root.setOnClickListener {
                                                    splitInstallManager?.let {
                                                        if (it.installedModules.contains("movie_detail")) {
//                                                            val i = Intent()
//                                                            i.setClassName(BuildConfig.APPLICATION_ID, "com.example.movie_detail.PostDetailActivity")
//                                                            startActivity(i)

                                                            val intent =
                                                                Intent(Intent.ACTION_VIEW)
                                                            intent.setData(Uri.parse("detail://example.com/moviedetail/654"))
                                                            startActivity(intent)


                                                        } else {
                                                            Log.e(tag, "Registration feature is not installed")
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                        spanSizeOverride { _, _, _ -> 3 }
                                    }
                                    is Popular.Result -> popularMovie {
                                        id(it.id)
                                        movie(it)
                                    }
                                }
                            }
                        }
                }

                Status.LOADING -> {

                }
                Status.ERROR -> {
                }
            }

        })

        return binding.root
    }


    private fun installRegistrationModule() {
        splitInstallManager = SplitInstallManagerFactory.create(context)
        val request = SplitInstallRequest.newBuilder()
            .addModule("movie_detail")
            .build()

        splitInstallManager?.startInstall(request)?.addOnSuccessListener {
                Log.d("Mukulpathak", it.toString())}
            ?.addOnFailureListener {
                Log.e("MukulPathak", it.toString())}
    }

}