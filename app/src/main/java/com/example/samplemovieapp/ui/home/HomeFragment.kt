package com.example.samplemovieapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.carousel
import com.example.samplemovieapp.Constants
import com.example.samplemovieapp.PopularMovieBindingModel_
import com.example.samplemovieapp.databinding.FragmentHomeBinding
import com.example.samplemovieapp.header
import com.example.samplemovieapp.models.Header
import com.example.samplemovieapp.models.Popular
import com.example.samplemovieapp.popularMovie
import com.example.samplemovieapp.utils.Status
import com.example.samplemovieapp.utils.withModelsFrom
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), LifecycleOwner {

    val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var binding = FragmentHomeBinding.inflate(LayoutInflater.from(context))
        binding.movies.setLayoutManager(GridLayoutManager(context, 3))

        homeViewModel.cacheSupport.observe(viewLifecycleOwner, Observer {
            binding.movies.withModels {
                it.forEach{
                    popularMovie {
                        id(it.id)
                        movie(it)
                    }
                }
            }
        })




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
                                            PopularMovieBindingModel_().id(movie.id).movie(movie)
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

}