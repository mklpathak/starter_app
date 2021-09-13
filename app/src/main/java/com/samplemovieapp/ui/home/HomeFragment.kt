package com.samplemovieapp.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.core.ModelTypes
import com.core.models.BaseModel

import com.core.models.BaseModelWrapper
import com.core.models.Header
import com.core.models.Popular
import com.core.ui.*
import com.core.utils.Resource
import com.core.utils.Status
import com.core.utils.carousel
import com.core.utils.withModelsFrom
import com.samplemovieapp.databinding.FragmentHomeBinding
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.samplemovieapp.PopularMovieBindingModel_
import com.samplemovieapp.dagger.inject
import com.samplemovieapp.databinding.ItemHorizontalListBinding
import com.samplemovieapp.databinding.ItemLoaderBinding
import com.samplemovieapp.header
import com.samplemovieapp.intent.MovieIntent
import com.samplemovieapp.popularMovie
import com.samplemovieapp.ui.binders.ErrorBinder
import com.samplemovieapp.ui.binders.HeadingBinder
import com.samplemovieapp.ui.binders.LoaderBinder
import com.samplemovieapp.ui.binders.MoviesListBinder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeFragment : Fragment(), LifecycleOwner {

    private var splitInstallManager: SplitInstallManager? = null

    lateinit var dataController: DataController<BaseModel>

    @Inject
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.inject()
//
//

//
        var binding = FragmentHomeBinding.inflate(LayoutInflater.from(context)).apply {

        }


        dataController = DataController<BaseModel>().apply {
            registerBinder(ErrorBinder())
            registerBinder(LoaderBinder())
            registerBinder(HeadingBinder())
            registerBinder(MoviesListBinder())
        }




        installRegistrationModule()
        dataController.setUpRecyclerView(binding.recyclerView,4)
        lifecycleScope.launch {
            homeViewModel.userIntent.send(MovieIntent.FetchMovies)
        }


        homeViewModel.liveData.observe(viewLifecycleOwner,{
            when(it) {
                is HomeState.Idle -> {}
                is HomeState.Movies -> {
                    dataController.getDataAdaptor().submitList(ArrayList(it.data))
                }
            }
        })



        return binding.root
    }

    private fun installRegistrationModule() {
        splitInstallManager = context?.let { SplitInstallManagerFactory.create(it) }
        val request = SplitInstallRequest.newBuilder()
            .addModule("movie_detail")
            .build()

        splitInstallManager?.startInstall(request)?.addOnSuccessListener {
                Log.d("Mukulpathak", it.toString())}
            ?.addOnFailureListener {
                Log.e("MukulPathak", it.toString())}
    }



}