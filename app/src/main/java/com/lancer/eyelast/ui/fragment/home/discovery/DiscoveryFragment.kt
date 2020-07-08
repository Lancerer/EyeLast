package com.lancer.eyelast.ui.fragment.home.discovery

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.databinding.FragmentDiscoveryBinding

/**
 * 发现
 */
class DiscoveryFragment : BaseFragment<FragmentDiscoveryBinding>() {


    override fun initView() {
    }

    override fun initData() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.discoveryMultiple.showEmpty()
    }
    override fun initLayout(): Int =R.layout.fragment_discovery

}