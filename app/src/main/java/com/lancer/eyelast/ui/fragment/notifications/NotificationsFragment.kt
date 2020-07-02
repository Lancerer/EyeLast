package com.lancer.eyelast.ui.fragment.notifications


import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.databinding.FragmentNotificationsBinding

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {

    private val tabList = ArrayList<String>().apply {
        add(getString(R.string.push))
        add(getString(R.string.interaction))
        add(getString(R.string.inbox))
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initLayout(): Int = R.layout.fragment_notifications


}