package com.lancer.eyelast.model

import com.lancer.eyelast.bean.VideoBeanForClient
import com.lancer.eyelast.bean.VideoRelated
import com.lancer.eyelast.bean.VideoReplies
import com.lancer.eyelast.model.dao.VideoDao
import com.lancer.eyelast.network.EyeLastNetWork
import com.lancer.eyelast.network.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * @author lancer
 * @des
 * @Date 2020/7/7 9:30
 */
class VideoRepository(private val dao: VideoDao, private val network: EyeLastNetWork) {

    /**
     * 视频详情-推荐列表
     */
    fun requestVideoRelated(id: Long): Observable<VideoRelated> {
        return network.fetchVideoRecommendList(id).compose(SchedulerUtils.ioToMain())
    }

    /**
     * TODO
     */
    fun requestVideoDetail(id: Long): Observable<VideoBeanForClient> {
        return network.fetchVideoDetail(id)
    }

    /**
     * 视频详情-评论列表
     */
    fun requestVideoReplies(url: String): Observable<VideoReplies> {
        return network.fetchVideoCommentList(url)
    }

    companion object {

        private var repository: VideoRepository? = null

        fun getInstance(dao: VideoDao, network: EyeLastNetWork): VideoRepository {
            if (repository == null) {
                synchronized(VideoRepository::class.java) {
                    if (repository == null) {
                        repository = VideoRepository(dao, network)
                    }
                }
            }

            return repository!!
        }
    }
}