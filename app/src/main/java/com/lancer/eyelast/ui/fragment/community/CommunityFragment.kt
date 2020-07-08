package com.lancer.eyelast.ui.fragment.community

import android.util.Log

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.base.BaseFragmentAdapter
import com.lancer.eyelast.databinding.FragmentCommunityBinding
import com.lancer.eyelast.ui.fragment.community.follow.FollowFragment
import com.lancer.eyelast.ui.fragment.community.recommend.RecommendFragment


class CommunityFragment : BaseFragment<FragmentCommunityBinding>() {
    init {
        name = CommunityFragment::class.java.simpleName
    }

    private val mTabList = ArrayList<String>().apply {
        add("推荐")
        add("关注")
    }

    private val mFragmentList = ArrayList<Fragment>().apply {
        add(RecommendFragment())
        add(FollowFragment())
    }

    override fun initView() {
        Log.d("TAG", "mTabList.size=${mTabList.size}")
        binding.communityVp.adapter =
            BaseFragmentAdapter(childFragmentManager, mFragmentList, mTabList,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )
        binding.communityTabLayout.setupWithViewPager(binding.communityVp)
    }

    override fun initData() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mTabList.clear()
        mFragmentList.clear()
        Log.d("TAG", "mTabList.size=${mTabList.size}")
    }

    override fun initLayout(): Int = R.layout.fragment_community


}