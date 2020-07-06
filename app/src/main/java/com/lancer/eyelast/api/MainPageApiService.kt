package com.lancer.eyelast.api

import com.lancer.eyelast.bean.Daily
import com.lancer.eyelast.bean.HomePageRecommend
import com.lancer.eyelast.bean.PushMessage
import com.lancer.eyelast.network.HttpControl
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @author lancer
 * @des
 * @Date 2020/7/3 8:50
 */
interface MainPageApiService {


    /**
     * 通知-推送列表
     */
    @GET
    fun getPushMessage(@Url url: String): Observable<PushMessage>

    /**
     * 首页-日报列表
     */
    @GET
    fun getDaily(@Url url: String):Observable<Daily>


    /**
     * 首页-推荐列表
     */
    @GET
    fun getHRecommend(@Url url: String): Observable<HomePageRecommend>

    companion object {

        /**
         * 首页-发现列表
         */
        const val DISCOVERY_URL = "${HttpControl.BASE_URL}api/v7/index/tab/discovery"

        /**
         * 首页-推荐列表
         */
        const val HOMEPAGE_RECOMMEND_URL = "${HttpControl.BASE_URL}api/v5/index/tab/allRec?page=0"

        /**
         * 首页-日报列表
         */
        const val DAILY_URL = "${HttpControl.BASE_URL}api/v5/index/tab/feed"

        /**
         * 社区-推荐列表
         */
        const val COMMUNITY_RECOMMEND_URL = "${HttpControl.BASE_URL}api/v7/community/tab/rec"

        /**
         * 社区-关注列表
         */
        const val FOLLOW_URL = "${HttpControl.BASE_URL}api/v6/community/tab/follow"

        /**
         * 通知-推送列表
         */
        const val PUSHMESSAGE_URL = "${HttpControl.BASE_URL}api/v3/messages"
    }
}