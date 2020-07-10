package com.lancer.eyelast.ui.activity.video

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lancer.eyelast.Const
import com.lancer.eyelast.R
import com.lancer.eyelast.bean.VideoReplies
import com.lancer.eyelast.extension.RecyclerViewHelp
import com.lancer.eyelast.extension.dp2px
import com.lancer.eyelast.extension.load
import com.lancer.eyelast.utils.DateUtil
import com.lancer.eyelast.utils.GlobalUtil

/**
 * @author lancer
 * @des 视频评论详情列表adapter
 * @Date 2020/7/7 17:01
 */
class VideoDetailCommentListAdapter(
    activity: VideoActivity,
    private var videoInfoData: VideoActivity.VideoInfo?
) :
    BaseProviderMultiAdapter<VideoReplies.Item>() {
    init {
        addItemProvider(TextCardViewHeader4ViewHolder(activity))
        addItemProvider(ReplyViewHolder(activity))
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun getItemType(data: List<VideoReplies.Item>, position: Int): Int {
        return when {
            data[position].type == "reply" && data[position].data.dataType == "ReplyBeanForClient" -> REPLY_BEAN_FOR_CLIENT_TYPE
            else -> RecyclerViewHelp.getItemViewType(data[position])
        }
    }

    private inner class TextCardViewHeader4ViewHolder(private val activity: VideoActivity) :
        BaseItemProvider<VideoReplies.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.TEXT_CARD_HEADER4
        override val layoutId: Int
            get() = R.layout.item_text_card_type_header_four

        override fun convert(helper: BaseViewHolder, item: VideoReplies.Item) {
            val text: TextView = helper.getView(R.id.tvTitle4)
            text.text = item.data.text
            if (item.data.actionUrl != null && item.data.actionUrl.startsWith(Const.ActionUrl.REPLIES_HOT)) {
                helper.setVisible(R.id.ivInto4, true)
                text.layoutParams = (text.layoutParams as LinearLayout.LayoutParams).apply {
                    setMargins(
                        0,
                        dp2px(30f),
                        0,
                        dp2px(6f)
                    )
                }

            } else {
                helper.setGone(R.id.ivInto4, true)
                text.layoutParams = (text.layoutParams as LinearLayout.LayoutParams).apply {
                    setMargins(
                        0,
                        dp2px(24f),
                        0,
                        dp2px(6f)
                    )
                }

            }
        }

    }

    private inner class ReplyViewHolder(private val activity: VideoActivity) :
        BaseItemProvider<VideoReplies.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.MAX
        override val layoutId: Int
            get() = R.layout.item_new_detail_reply_type

        override fun convert(helper: BaseViewHolder, item: VideoReplies.Item) {
            val ivAvatar: ImageView = helper.getView(R.id.ivAvatar)
            val ivReplyAvatar: ImageView = helper.getView(R.id.ivReplyAvatar)
            ivAvatar.load(item.data.user?.avatar ?: "")
            helper.setText(R.id.tvNickName, item.data.user?.nickname)
            helper.setText(R.id.tvMessage, getTimeReply(item.data.createTime))
            helper.setText(R.id.tvTime, item.data.message)
            helper.setText(
                R.id.tvLikeCount,
                if (item.data.likeCount == 0) "" else item.data.likeCount.toString()
            )
            item.data.parentReply?.run {
                helper.setVisible(R.id.groupReply, true)
                helper.setText(
                    R.id.tvReplyUser,
                    String.format(GlobalUtil.getString(R.string.reply_target), user?.nickname)
                )
                ivReplyAvatar.load(user?.avatar ?: "")
                helper.setText(R.id.tvReplyNickName, user?.nickname)
                helper.setText(R.id.tvReplyMessage, message)
            }
        }

    }

    private fun getTimeReply(dateMillis: Long): String {
        val currentMillis = System.currentTimeMillis()
        val timePast = currentMillis - dateMillis
        if (timePast > -DateUtil.MINUTE) { // 采用误差一分钟以内的算法，防止客户端和服务器时间不同步导致的显示问题
            when {
                timePast < DateUtil.DAY -> {
                    var pastHours = timePast / DateUtil.HOUR
                    if (pastHours <= 0) {
                        pastHours = 1
                    }
                    return DateUtil.getDate(dateMillis, "HH:mm")
                }
                else -> return DateUtil.getDate(dateMillis, "yyyy/MM/dd")
            }
        } else {
            return DateUtil.getDate(dateMillis, "yyyy/MM/dd HH:mm")
        }
    }

    companion object {
        const val TAG = "NewDetailReplyAdapter"
        const val REPLY_BEAN_FOR_CLIENT_TYPE = Const.ItemViewType.MAX
    }
}