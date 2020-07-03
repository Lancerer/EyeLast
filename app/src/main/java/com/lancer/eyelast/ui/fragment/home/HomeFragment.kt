package com.lancer.eyelast.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.base.BaseFragmentAdapter
import com.lancer.eyelast.databinding.FragmentHomeBinding
import com.lancer.eyelast.ui.fragment.home.commend.CommendFragment
import com.lancer.eyelast.ui.fragment.home.daily.DailyFragment
import com.lancer.eyelast.ui.fragment.home.discovery.DiscoveryFragment
import com.lancer.eyelast.ui.fragment.notifications.NotificationsFragment
import com.lancer.eyelast.ui.fragment.notifications.interactive.InteractionFragment
import com.lancer.eyelast.ui.fragment.notifications.letter.LetterFragment
import com.lancer.eyelast.ui.fragment.notifications.push.PushFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    init {
        name = NotificationsFragment::class.java.simpleName
    }

    private val mTabList = ArrayList<String>().apply {
        add("发现")
        add("推荐")
        add("日报")
    }

    private val mFragmentList = ArrayList<Fragment>().apply {
        add(DiscoveryFragment())
        add(CommendFragment())
        add(DailyFragment())
    }

    override fun initView() {
        Log.d("TAG", "mTabList.size=${mTabList.size}")
        binding.homeVp.adapter =
            BaseFragmentAdapter(childFragmentManager, mFragmentList, mTabList)
        binding.homeTabLayout.setupWithViewPager(binding.homeVp)
    }

    override fun initData() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mTabList.clear()
        mFragmentList.clear()
        Log.d("TAG", "mTabList.size=${mTabList.size}")
    }

    override fun initLayout(): Int = R.layout.fragment_home

}