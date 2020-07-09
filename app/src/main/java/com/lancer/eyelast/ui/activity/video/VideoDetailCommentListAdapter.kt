package com.lancer.eyelast.ui.activity.video

import android.provider.MediaStore
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lancer.eyelast.bean.VideoRelated
import com.lancer.eyelast.bean.VideoReplies

/**
 * @author lancer
 * @des 视频详情评论列表adapter
 * @Date 2020/7/7 17:01
 */
class VideoDetailCommentListAdapter : BaseProviderMultiAdapter<VideoReplies.Item>() {
    init {


    }

    override fun getItemType(data: List<VideoReplies.Item>, position: Int): Int {
        TODO("Not yet implemented")
    }

    private inner class CustomHeaderViewHolder : BaseItemProvider<VideoReplies.Item>() {
        override val itemViewType: Int
            get() = TODO("Not yet implemented")
        override val layoutId: Int
            get() = TODO("Not yet implemented")

        override fun convert(helper: BaseViewHolder, item: VideoReplies.Item) {
            TODO("Not yet implemented")
        }

    }

    private inner class SimpleHotReplyCardViewHolder : BaseItemProvider<VideoReplies.Item>() {
        override val itemViewType: Int
            get() = TODO("Not yet implemented")
        override val layoutId: Int
            get() = TODO("Not yet implemented")

        override fun convert(helper: BaseViewHolder, item: VideoReplies.Item) {
            TODO("Not yet implemented")
        }

    }

    private inner class VideoSmallCardViewHolder : BaseItemProvider<VideoReplies.Item>() {
        override val itemViewType: Int
            get() = TODO("Not yet implemented")
        override val layoutId: Int
            get() = TODO("Not yet implemented")

        override fun convert(helper: BaseViewHolder, item: VideoReplies.Item) {
            TODO("Not yet implemented")
        }

    }

    private inner class TextCardViewHeader4ViewHolder : BaseItemProvider<VideoReplies.Item>() {
        override val itemViewType: Int
            get() = TODO("Not yet implemented")
        override val layoutId: Int
            get() = TODO("Not yet implemented")

        override fun convert(helper: BaseViewHolder, item: VideoReplies.Item) {
            TODO("Not yet implemented")
        }

    }
}