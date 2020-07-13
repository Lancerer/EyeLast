package com.lancer.eyelast.ui.fragment.notifications.interactive

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.databinding.FragmentInteractionBinding
import com.lancer.eyelast.ui.activity.login.LoginActivity

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
        binding.login.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
    }

    override fun initLayout(): Int = R.layout.fragment_interaction

}