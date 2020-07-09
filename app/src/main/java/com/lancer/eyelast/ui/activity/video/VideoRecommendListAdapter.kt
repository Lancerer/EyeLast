package com.lancer.eyelast.ui.activity.video

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.lancer.eyelast.bean.VideoRelated

/**
 * @author lancer
 * @des 视频详情推荐列表adapter
 * @Date 2020/7/7 17:00
 */
class VideoRecommendListAdapter : BaseProviderMultiAdapter<VideoRelated.Item>() {
    init {

    }

    override fun getItemType(data: List<VideoRelated.Item>, position: Int): Int {
        TODO("Not yet implemented")
    }
}