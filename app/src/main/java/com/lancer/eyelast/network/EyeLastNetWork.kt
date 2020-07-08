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


    /**
     * 推送
     */
    fun fetchPushMessage(url: String) = mainPageService.getPushMessage(url)

    /**
     * 日报
     */
    fun fetchDaily(url: String) = mainPageService.getDaily(url)

    /**
     * 推荐
     */
    fun fetchRecommend(url: String) = mainPageService.getHRecommend(url)

    /**
     * 视频详情-推荐列表
     */
    fun fetchVideoRecommendList(videoId: Long) = mainPageService.getVideoRecommendList(videoId)

    /**
     * 视频详情-评论列表
     */
    fun fetchVideoCommentList(url: String) = mainPageService.getVideoCommentList(url)

    /**
     * 社区模块-关注列表follow
     */
    fun fetchFollowList(url: String) = mainPageService.getFollowList(url)

    /**
     * 社区模块-推荐列表
     */
    fun fetchCommunityRecommend(url: String) = mainPageService.getCommunityRecommend(url)

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