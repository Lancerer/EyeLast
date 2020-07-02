package com.lancer.eyelast.model

import com.lancer.eyelast.model.dao.MainPageDao
import com.lancer.eyelast.network.EyeLastNetWork

/**
 * @author lancer
 * @des 主页界面，主要包含：（首页，社区，通知，我的），对应的数据管理,两部分：数据库和网络数据
 * @Date 2020/7/2 15:51
 */
class MainPageRepository private constructor(private val mainPageDao: MainPageDao, private val eyeLastNetWork: EyeLastNetWork) {

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