package com.lancer.eyelast.model.dao

/**
 * @author lancer
 * @des
 * @Date 2020/7/3 9:09
 */
object EyeLastDatabase {
    private var mainPageDao: MainPageDao? = null

    private var videoDao: VideoDao? = null

    fun getMainPageDao(): MainPageDao {
        if (mainPageDao == null) {
            mainPageDao = MainPageDao()
        }
        return mainPageDao!!
    }

    fun getVideoDao(): VideoDao {
        if (videoDao == null) {
            videoDao = VideoDao()
        }
        return videoDao!!
    }
}