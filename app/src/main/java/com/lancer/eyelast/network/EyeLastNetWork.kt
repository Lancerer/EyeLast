package com.lancer.eyelast.network

import com.lancer.eyelast.api.MainPageApiService

/**
 * @author lancer
 * @des 管理网络请求的类
 * @Date 2020/7/2 13:16
 */
class EyeLastNetWork {

    private val mainPageService =
        HttpControl.getInstance(HttpControl.BASE_URL).create(MainPageApiService::class.java)


    fun fetchPushMessage(url: String) = mainPageService.getPushMessage(url)


    companion object {

        private var network: EyeLastNetWork? = null

        fun getInstance(): EyeLastNetWork {
            if (network == null) {
                synchronized(EyeLastNetWork::class.java) {
                    if (network == null) {
                        network = EyeLastNetWork()
                    }
                }
            }
            return network!!
        }
    }

}