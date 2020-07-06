package com.lancer.eyelast.utils

import com.lancer.eyelast.model.MainPageRepository
import com.lancer.eyelast.model.dao.EyeLastDatabase
import com.lancer.eyelast.network.EyeLastNetWork
import com.lancer.eyelast.ui.fragment.home.commend.CommendViewModelFactory
import com.lancer.eyelast.ui.fragment.home.daily.DailyViewModelFactory
import com.lancer.eyelast.ui.fragment.notifications.push.PushViewModelFactory

/**
 * @author lancer
 * @des
 * @Date 2020/7/3 9:07
 */
object InjectorUtil {
    private fun getMainPageRepository() = MainPageRepository.getInstance(
        EyeLastDatabase.getMainPageDao(),
        EyeLastNetWork.getInstance()
    )

    /**
     * push
     */
    fun getPushViewModelFactory() = PushViewModelFactory(getMainPageRepository())

    /**
     * daily
     */
    fun getDailyViewModelFactory() = DailyViewModelFactory(getMainPageRepository())


    /**
     * commend
     */
    fun getHomePageCommendViewModelFactory() = CommendViewModelFactory(getMainPageRepository())

}