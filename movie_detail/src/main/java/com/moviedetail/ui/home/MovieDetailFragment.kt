package com.moviedetail.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.moviedetail.databinding.FragmentMovieDetailBinding
import com.samplemovieapp.dagger.injectpost
import javax.inject.Inject


class MovieDetailFragment : Fragment(), LifecycleOwner {

    @Inject
    lateinit var postDetailViewModel: MovieDetailViewModel

    val args : MovieDetailFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injectpost(this)
        postDetailViewModel.refreshData(args.id)
        var binding = FragmentMovieDetailBinding.inflate(LayoutInflater.from(context))
        postDetailViewModel.res.observe(viewLifecycleOwner, Observer {
            binding.movieDetail = it.data
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}