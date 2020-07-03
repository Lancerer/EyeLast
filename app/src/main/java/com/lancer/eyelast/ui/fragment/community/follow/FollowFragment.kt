package com.lancer.eyelast.ui.fragment.community.follow

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lancer.eyelast.R

class FollowFragment : Fragment() {

    companion object {
        fun newInstance() = FollowFragment()
    }

    private lateinit var viewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FollowViewModel::class.java)
        // TODO: Use the ViewModel
    }

}