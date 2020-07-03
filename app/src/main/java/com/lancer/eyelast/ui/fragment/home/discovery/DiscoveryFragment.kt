package com.lancer.eyelast.ui.fragment.home.discovery

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lancer.eyelast.R

/**
 * 发现
 */
class DiscoveryFragment : Fragment() {

    companion object {
        fun newInstance() = DiscoveryFragment()
    }

    private lateinit var viewModel: DiscoveryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discovery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DiscoveryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}