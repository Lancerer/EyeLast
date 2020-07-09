package com.lancer.eyelast.ui.fragment.home.daily

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.lancer.eyelast.bean.Daily
import com.lancer.eyelast.ui.fragment.home.daily.provider.TextCardViewHeader5ViewHolder
import com.lancer.eyelast.extension.RecyclerViewHelp
import com.lancer.eyelast.ui.fragment.home.daily.provider.Banner3ViewHolder
import com.lancer.eyelast.ui.fragment.home.daily.provider.FollowCardViewHolder
import com.lancer.eyelast.ui.fragment.home.daily.provider.InformationCardFollowCardViewHolder
import com.lancer.eyelast.ui.fragment.home.daily.provider.TextCardViewFooter2ViewHolder
import com.lancer.eyelast.ui.fragment.home.daily.provider.TextCardViewFooter3ViewHolder
import com.lancer.eyelast.ui.fragment.home.daily.provider.TextCardViewHeader7ViewHolder
import com.lancer.eyelast.ui.fragment.home.daily.provider.TextCardViewHeader8ViewHolder

/**
 * @author lancer
 * @des
 * @Date 2020/7/3 15:09
 */
class DailyAdapter(fragment: DailyFragment) :
    BaseProviderMultiAdapter<Daily.Item>(){
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