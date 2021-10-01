package com.samplemovieapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope

import com.core.ui.*
import com.samplemovieapp.databinding.FragmentHomeBinding
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.samplemovieapp.dagger.inject
import com.samplemovieapp.intent.MovieIntent
import com.samplemovieapp.ui.binders.ErrorBinder
import com.samplemovieapp.ui.binders.HeadingBinder
import com.samplemovieapp.ui.binders.LoaderBinder
import com.samplemovieapp.ui.binders.MoviesListBinder
import kotlinx.coroutines.launch
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
        this.inject()
        val binding = FragmentHomeBinding.inflate(LayoutInflater.from(context))
        val adaptor =  AdaptiveList<BaseModel>().apply {
            registerViewHolders(ErrorBinder())
            registerViewHolders(LoaderBinder())
            registerViewHolders(HeadingBinder())
            registerViewHolders(MoviesListBinder())
            setUpRecyclerView(binding.recyclerView,4)
        }
        installRegistrationModule()
        lifecycleScope.launch {
            homeViewModel.userIntent.send(MovieIntent.FetchMovies)
        }
        homeViewModel.liveData.observe(viewLifecycleOwner,{
            when(it) {
                is HomeState.Idle -> {}
                is HomeState.Movies -> {
                    adaptor.submitList(ArrayList(it.data))
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