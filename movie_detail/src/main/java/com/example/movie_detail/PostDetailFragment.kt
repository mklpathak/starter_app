package com.example.movie_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner

import androidx.navigation.fragment.findNavController
import com.example.samplemovieapp.dagger.inject
import com.example.samplemovieapp.dagger.injectpost
import javax.inject.Inject


class PostDetailFragment : Fragment() , LifecycleOwner {

    @Inject
    lateinit var postDetailViewModel:PostDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        injectpost(this)

        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postDetailViewModel.refreshData(483)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}