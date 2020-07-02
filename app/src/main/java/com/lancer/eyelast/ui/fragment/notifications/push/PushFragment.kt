package com.lancer.eyelast.ui.fragment.notifications.push

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.databinding.FragmentPushBinding
import com.lancer.eyelast.ui.fragment.notifications.letter.LetterFragment

class PushFragment : BaseFragment<FragmentPushBinding>() {
    init {
        name = PushFragment::class.java.simpleName
    }

    companion object {
        fun newInstance() = PushFragment()
    }

    private lateinit var viewModel: PushViewModel
    private lateinit var mAdapter: PushAdapter
    override fun initView() {
        mAdapter = PushAdapter()
        binding.pushRv.layoutManager = LinearLayoutManager(context)
        binding.pushRv.adapter = mAdapter
    }

    override fun initData() {
    }

    override fun initLayout(): Int = R.layout.fragment_push

}