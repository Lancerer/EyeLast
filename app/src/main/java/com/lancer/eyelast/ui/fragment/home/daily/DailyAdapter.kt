package com.lancer.eyelast.ui.fragment.home.daily

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.lancer.eyelast.Const
import com.lancer.eyelast.Const.ItemViewType.Companion.BANNER3
import com.lancer.eyelast.Const.ItemViewType.Companion.FOLLOW_CARD
import com.lancer.eyelast.Const.ItemViewType.Companion.INFORMATION_CARD
import com.lancer.eyelast.Const.ItemViewType.Companion.TEXT_CARD_FOOTER2
import com.lancer.eyelast.Const.ItemViewType.Companion.TEXT_CARD_FOOTER3
import com.lancer.eyelast.Const.ItemViewType.Companion.TEXT_CARD_HEADER5
import com.lancer.eyelast.Const.ItemViewType.Companion.TEXT_CARD_HEADER8
import com.lancer.eyelast.Const.ItemViewType.Companion.UNKNOWN
import com.lancer.eyelast.bean.Daily
import com.lancer.eyelast.ui.provider.*
import com.lancer.eyelast.extension.RecyclerViewHelp

/**
 * @author lancer
 * @des
 * @Date 2020/7/3 15:09
 */
class DailyAdapter(fragment: DailyFragment) : BaseProviderMultiAdapter<Daily.Item>(),
    LoadMoreModule {
    init {
        addItemProvider(Banner3ViewHolder(fragment))
        addItemProvider(FollowCardViewHolder(fragment))
        addItemProvider(InformationCardFollowCardViewHolder(fragment))
        addItemProvider(TextCardViewFooter2ViewHolder(fragment))
        addItemProvider(TextCardViewFooter3ViewHolder(fragment))
        addItemProvider(TextCardViewHeader5ViewHolder(fragment))
        addItemProvider(TextCardViewHeader7ViewHolder(fragment))
        addItemProvider(TextCardViewHeader8ViewHolder(fragment))
    }

    override fun getItemType(data: List<Daily.Item>, position: Int): Int {
        return RecyclerViewHelp.getItemViewType(data[position])
    }
    companion object {
        const val TAG = "DailyAdapter"
        const val DEFAULT_LIBRARY_TYPE = "DEFAULT"
        const val NONE_LIBRARY_TYPE = "NONE"
        const val DAILY_LIBRARY_TYPE = "DAILY"
    }
}