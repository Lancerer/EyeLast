package com.lancer.eyelast.ui.fragment.community.follow

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lancer.eyelast.BuildConfig
import com.lancer.eyelast.Const
import com.lancer.eyelast.R
import com.lancer.eyelast.bean.Follow
import com.lancer.eyelast.extension.*
import com.lancer.eyelast.utils.DateUtil
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer

/**
 * @author lancer
 * @des
 * @Date 2020/7/8 8:50
 */
class FollowAdapter(val fragment: FollowFragment, var dataList: List<Follow.Item>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        return when {
            dataList[position].type == "autoPlayFollowCard" && dataList[position].data.dataType == "FollowCard" -> AUTO_PLAY_FOLLOW_CARD
            else -> Const.ItemViewType.UNKNOWN
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AUTO_PLAY_FOLLOW_CARD -> AutoPlayFollowCardViewHolder(
                R.layout.item_community_auto_play_follow_card_follow_card_type.inflate(
                    parent
                )
            )
            else -> EmptyViewHolder(View(parent.context))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is AutoPlayFollowCardViewHolder -> {
                val item = dataList[position]
                item.data.content.data.run {
                    holder.ivAvatar.load(item.data.header.icon ?: author?.icon ?: "")

                    holder.tvReleaseTime.text = DateUtil.getDate(
                        releaseTime ?: author?.latestReleaseTime ?: System.currentTimeMillis(),
                        "HH:mm"
                    )
                    holder.tvTitle.text = title
                    holder.tvNickname.text = author?.name ?: ""
                    holder.tvContent.text = description
                    holder.tvCollectionCount.text = consumption.collectionCount.toString()
                    holder.tvReplyCount.text = consumption.replyCount.toString()
                    holder.tvVideoDuration.visible()    //视频播放后，复用tvVideoDuration直接隐藏了
                    holder.tvVideoDuration.text = duration.conversionVideoDuration()
                }

                //TODO
            }
            else -> {
                holder.itemView.gone()
                if (BuildConfig.DEBUG) "${TAG}:${Const.Toast.BIND_VIEWHOLDER_TYPE_WARN}\n${holder}".showToast()
            }
        }
    }


    inner class AutoPlayFollowCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvatar: ImageView = view.findViewById(R.id.ivAvatar)
        val tvReleaseTime: TextView = view.findViewById(R.id.tvReleaseTime)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvNickname: TextView = view.findViewById(R.id.tvNickname)
        val tvContent: TextView = view.findViewById(R.id.tvContent)
        val ivCollectionCount: ImageView = view.findViewById<ImageView>(R.id.ivCollectionCount)
        val tvCollectionCount: TextView = view.findViewById(R.id.tvCollectionCount)
        val ivReply: ImageView = view.findViewById(R.id.ivReply)
        val tvReplyCount: TextView = view.findViewById(R.id.tvReplyCount)
        val ivFavorites: ImageView = view.findViewById(R.id.ivFavorites)
        val tvFavorites: TextView = view.findViewById(R.id.tvFavorites)
        val tvVideoDuration: TextView = view.findViewById(R.id.tvVideoDuration)
        val ivShare: ImageView = view.findViewById(R.id.ivShare)
        val videoPlayer: GSYVideoPlayer = view.findViewById(R.id.videoPlayer)
    }

    companion object {
        const val TAG = "FollowAdapter"
        const val AUTO_PLAY_FOLLOW_CARD =
            Const.ItemViewType.MAX    //type:autoPlayFollowCard -> dataType:FollowCard
    }
}
