package com.lancer.eyelast.ui.activity.video

import android.widget.ImageView
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lancer.eyelast.Const
import com.lancer.eyelast.R
import com.lancer.eyelast.bean.VideoRelated
import com.lancer.eyelast.extension.RecyclerViewHelp
import com.lancer.eyelast.extension.load
import com.lancer.eyelast.ui.fragment.home.daily.DailyAdapter

/**
 * @author lancer
 * @des 视频详情推荐列表adapter
 * @Date 2020/7/7 17:00
 */
class VideoRecommendListAdapter(
    private val activity: VideoActivity,
    private var videoInfoData: VideoActivity.VideoInfo?
) : BaseProviderMultiAdapter<VideoRelated.Item>() {

    init {
        addItemProvider(CustomHeaderViewHolder(activity))
        addItemProvider(VideoSmallCardViewHolder(activity))
        addItemProvider(TextCardViewHeader4ViewHolder(activity))
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    override fun getItemType(data: List<VideoRelated.Item>, position: Int): Int {
        return when {
            position == 0 -> Const.ItemViewType.CUSTOM_HEADER
            data[position - 1].type == "simpleHotReplyScrollCard" && data[position - 1].data.type == "ItemCollection" -> SIMPLE_HOT_REPLY_CARD_TYPE
            else -> RecyclerViewHelp.getItemViewType(data[position])
        }
    }

    private inner class CustomHeaderViewHolder(private val activity: VideoActivity) :
        BaseItemProvider<VideoRelated.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.CUSTOM_HEADER
        override val layoutId: Int
            get() = R.layout.item_new_detail_custom_header_type

        override fun convert(helper: BaseViewHolder, item: VideoRelated.Item) {
            videoInfoData?.let {
                helper.run {
                    helper.setGone(R.id.groupAuthor, true)
                    helper.setText(R.id.tvTitle, it.title)
                    helper.setText(R.id.tvDescription, it.description)
                    helper.setText(
                        R.id.tvCollectionCount,
                        it.consumption.collectionCount.toString()
                    )
                    helper.setText(R.id.tvShareCount, it.consumption.shareCount.toString())

                    helper.setText(
                        R.id.tvCategory,
                        if (videoInfoData?.library == DailyAdapter.DAILY_LIBRARY_TYPE) "#${videoInfoData?.category} / 开眼精选" else "#${videoInfoData?.category}"
                    )

                    it.author?.run {
                        helper.setText(R.id.tvAuthorDescription, it.author.description)
                        helper.setText(R.id.tvAuthorName, it.author.name)
                        helper.setVisible(R.id.groupAuthor, true)
                        val ivAvatar = helper.getView<ImageView>(R.id.ivAvatar)
                        ivAvatar.load(it.author.icon ?: "")
                    }
                    //TODO setOnclickListener


                }
            }
        }

    }

    private inner class SimpleHotReplyCardViewHolder(private val activity: VideoActivity) :
        BaseItemProvider<VideoRelated.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.MAX
        override val layoutId: Int
            get() = R.layout.layout_common_multiple_refresh_recycler

        override fun convert(helper: BaseViewHolder, item: VideoRelated.Item) {
            // TODO("Not yet implemented")
        }

    }

    private inner class VideoSmallCardViewHolder(private val activity: VideoActivity) :
        BaseItemProvider<VideoRelated.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.VIDEO_SMALL_CARD
        override val layoutId: Int
            get() = R.layout.item_video_small_card_type

        override fun convert(helper: BaseViewHolder, item: VideoRelated.Item) {
            val ivPicture: ImageView = helper.getView(R.id.ivPicture)
        }

    }

    private inner class TextCardViewHeader4ViewHolder(private val activity: VideoActivity) :
        BaseItemProvider<VideoRelated.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.TEXT_CARD_HEADER4
        override val layoutId: Int
            get() = R.layout.item_text_card_type_header_four

        override fun convert(helper: BaseViewHolder, item: VideoRelated.Item) {
            helper.setText(R.id.tvTitle4, item.data.text)
        }

    }

    companion object {
        const val TAG = "VideoRecommendListAdapter"
        const val SIMPLE_HOT_REPLY_CARD_TYPE = Const.ItemViewType.MAX
    }
}