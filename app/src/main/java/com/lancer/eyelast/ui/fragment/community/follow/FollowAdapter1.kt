package com.lancer.eyelast.ui.fragment.community.follow

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lancer.eyelast.Const
import com.lancer.eyelast.R
import com.lancer.eyelast.bean.Follow

/**
 * @author lancer
 * @des
 * @Date 2020/7/13 10:52
 */
class FollowAdapter1(val fragment: FollowFragment) : BaseProviderMultiAdapter<Follow.Item>() {

    init {

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemType(data: List<Follow.Item>, position: Int): Int {
        return when {
            data[position].type == "autoPlayFollowCard" && data[position].data.dataType == "FollowCard" -> FollowAdapter.AUTO_PLAY_FOLLOW_CARD
            else -> Const.ItemViewType.UNKNOWN
        }
    }

    private inner class AutoPlayFollowCardViewHolder : BaseItemProvider<Follow.Item>() {
        override val itemViewType: Int
            get() = AUTO_PLAY_FOLLOW_CARD
        override val layoutId: Int
            get() = R.layout.item_community_auto_play_follow_card_follow_card_type

        override fun convert(helper: BaseViewHolder, item: Follow.Item) {
        }

    }


    companion object {
        const val TAG = "FollowAdapter"
        const val AUTO_PLAY_FOLLOW_CARD =
            Const.ItemViewType.MAX    //type:autoPlayFollowCard -> dataType:FollowCard
    }
}