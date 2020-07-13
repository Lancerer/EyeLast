package com.lancer.eyelast.ui.fragment.notifications


import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.appbar.AppBarLayout
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.base.BaseFragmentAdapter
import com.lancer.eyelast.databinding.FragmentNotificationsBinding
import com.lancer.eyelast.ui.fragment.notifications.interactive.InteractionFragment
import com.lancer.eyelast.ui.fragment.notifications.letter.LetterFragment
import com.lancer.eyelast.ui.fragment.notifications.push.PushFragment

/**
 * 通知模块
 */
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {
    init {
        name = NotificationsFragment::class.java.simpleName
    }

    private val mTabList = ArrayList<String>().apply {
        add("推送")
        add("互动")
        add("私信")
    }

    private val mFragmentList = ArrayList<Fragment>().apply {
        add(PushFragment())
        add(LetterFragment())
        add(InteractionFragment())
    }

    override fun initView() {
        Log.d("TAG", "mTabList.size=${mTabList.size}")
        binding.notificationVp.adapter =
            BaseFragmentAdapter(childFragmentManager, mFragmentList, mTabList,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )
        binding.notificationTabLayout.setupWithViewPager(binding.notificationVp)
    }

    override fun initData() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mTabList.clear()
        mFragmentList.clear()
        Log.d("TAG", "mTabList.size=${mTabList.size}")
    }

    override fun initLayout(): Int = R.layout.fragment_notifications


}