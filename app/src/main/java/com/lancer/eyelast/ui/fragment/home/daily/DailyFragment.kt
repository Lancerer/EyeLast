package com.lancer.eyelast.ui.fragment.home.daily

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.databinding.FragmentDailyBinding

/**
 * 日报
 */
class DailyFragment : BaseFragment<FragmentDailyBinding>() {

    companion object {
        fun newInstance() = DailyFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dailyMultiple.showLoading()
    }
    override fun initView() {
    }

    override fun initData() {
    }

    override fun initLayout(): Int =R.layout.fragment_daily

}