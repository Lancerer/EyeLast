package com.lancer.eyelast.ui.fragment.notifications.interactive

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.databinding.FragmentInteractionBinding

class InteractionFragment : BaseFragment<FragmentInteractionBinding>() {
    init {
        name = InteractionFragment::class.java.simpleName
    }

    companion object {
        fun newInstance() = InteractionFragment()
    }

    private lateinit var viewModel: InteractionViewModel


    override fun initView() {
    }

    override fun initData() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.interactionMultiple.showEmpty()
    }

    override fun initLayout(): Int = R.layout.fragment_interaction

}