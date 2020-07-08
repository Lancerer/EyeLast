package com.lancer.eyelast.model

import com.lancer.eyelast.bean.*
import com.lancer.eyelast.model.dao.MainPageDao
import com.lancer.eyelast.network.EyeLastNetWork
import com.lancer.eyelast.network.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * @author lancer
 * @des 主页界面，主要包含：（首页，社区，通知，我的），对应的数据管理,两部分：数据库和网络数据
 * @Date 2020/7/2 15:51
 */
class MainPageRepository private constructor(
    private val mainPageDao: MainPageDao,
    private val eyeLastNetWork: EyeLastNetWork
) {


    fun requestPushMessage(url: String): Observable<PushMessage?>? {
        return eyeLastNetWork.fetchPushMessage(url).compose(SchedulerUtils.ioToMain())
    }

    fun requestDaily(url: String): Observable<Daily>? {
        return eyeLastNetWork.fetchDaily(url).compose(SchedulerUtils.ioToMain())
    }

    fun requestRecommend(url: String): Observable<HomePageRecommend>? {
        return eyeLastNetWork.fetchRecommend(url).compose(SchedulerUtils.ioToMain())
    }

    fun requestFollowList(url: String): Observable<Follow>? {
        return eyeLastNetWork.fetchFollowList(url).compose(SchedulerUtils.ioToMain())
    }

    fun requestCommunityRecommend(url: String): Observable<CommunityRecommend>? {
        return eyeLastNetWork.fetchCommunityRecommend(url).compose(SchedulerUtils.ioToMain())
    }


    companion object {
        private var repository: MainPageRepository? = null

        fun getInstance(dao: MainPageDao, network: EyeLastNetWork): MainPageRepository {
            if (repository == null) {
                synchronized(MainPageRepository::class.java) {
                    if (repository == null) {
                        repository = MainPageRepository(dao, network)
                    }
                }
            }
            return repository!!
        }
    }


}