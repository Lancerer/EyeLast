package com.lancer.eyelast.ui.activity.video

import androidx.lifecycle.ViewModel
import com.lancer.eyelast.Const
import com.lancer.eyelast.api.MainPageApiService
import com.lancer.eyelast.bean.VideoDetail
import com.lancer.eyelast.bean.VideoRelated
import com.lancer.eyelast.bean.VideoReplies
import com.lancer.eyelast.model.VideoRepository
import com.lancer.eyelast.network.scheduler.BaseSubscriber
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener

/**
 * @author lancer
 * @des 先请求视频详情，有了详情内的信息才能查看推荐视频和评论
 * @Date 2020/7/7 9:24
 */
class VideoViewModel(private val repository: VideoRepository) : ViewModel() {
    var relatedDataList = ArrayList<VideoRelated.Item>()

    var repliesDataList = ArrayList<VideoReplies.Item>()

    var videoInfoData: VideoActivity.VideoInfo? = null

    var videoId: Long = 0L


    fun requestVideoDetail(
        listener: OnNextWithErrorListener<VideoDetail>,
        id: Long,
        url: String = MainPageApiService.VIDEO_REPLIES_URL
    ) {


    }

    /**
     * 单独请求回复内容列表
     */
    fun requestCommentDetailList(listener: OnNextWithErrorListener<VideoReplies>, url: String) {
        repository.requestVideoReplies(url).subscribe(
            BaseSubscriber(object : OnNextWithErrorListener<VideoReplies> {
                override fun onNext(t: VideoReplies?) {
                    listener.onNext(t)
                }

                override fun onError(e: Throwable?) {
                    listener.onError(e)
                }

            })
        )
    }

    /**
     * 单独请求推荐视频信息列表
     */

    fun requestRecommendList(listener: OnNextWithErrorListener<VideoRelated>, id: Long) {
        repository.requestVideoRelated(id).subscribe(
            BaseSubscriber(object : OnNextWithErrorListener<VideoRelated> {
                override fun onNext(t: VideoRelated?) {
                    listener.onNext(t)
                }

                override fun onError(e: Throwable?) {
                    listener.onError(e)
                }

            })
        )
    }


    inner class RequestParam(val videoId: Long, val repliesUrl: String)

}