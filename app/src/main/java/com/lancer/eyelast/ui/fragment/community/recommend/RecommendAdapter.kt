package com.lancer.eyelast.ui.fragment.community.recommend

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.lancer.eyelast.Const
import com.lancer.eyelast.R
import com.lancer.eyelast.bean.CommunityRecommend
import com.lancer.eyelast.extension.*
import com.lancer.eyelast.ui.activity.ugc.UgcDetailActivity
import com.lancer.eyelast.ui.fragment.home.daily.DailyAdapter
import com.lancer.eyelast.utils.ActionUrl
import com.lancer.eyelast.utils.GlobalUtil
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

/**
 * @author lancer
 * @des
 * @Date 2020/7/8 10:02
 */
class RecommendAdapter(val fragment: RecommendFragment) :
    BaseProviderMultiAdapter<CommunityRecommend.Item>() {
    init {
        addItemProvider(HorizontalScrollCardItemCollectionViewHolder(fragment))
        addItemProvider(HorizontalScrollCardViewHolder(fragment))
        addItemProvider(FollowCardViewHolder(fragment))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemType(data: List<CommunityRecommend.Item>, position: Int): Int {
        return when (data[position].type) {
            STR_HORIZONTAL_SCROLLCARD_TYPE -> {
                when (data[position].data.dataType) {
                    STR_HORIZONTAL_SCROLLCARD_DATA_TYPE -> HORIZONTAL_SCROLLCARD_TYPE
                    STR_ITEM_COLLECTION_DATA_TYPE -> HORIZONTAL_SCROLLCARD_ITEM_COLLECTION_TYPE
                    else -> Const.ItemViewType.UNKNOWN
                }
            }
            STR_COMMUNITY_COLUMNS_CARD -> {
                if (data[position].data.dataType == STR_FOLLOW_CARD_DATA_TYPE) FOLLOW_CARD_TYPE
                else Const.ItemViewType.UNKNOWN
            }
            else -> {
                Const.ItemViewType.UNKNOWN
            }
        }
    }

    private inner class HorizontalScrollCardItemCollectionViewHolder(val fragment: RecommendFragment):BaseItemProvider<CommunityRecommend.Item>(){
        override val itemViewType: Int
            get() = HORIZONTAL_SCROLLCARD_ITEM_COLLECTION_TYPE
        override val layoutId: Int
            get() = R.layout.item_community_horizontal_scrollcard_item_collection_type

        override fun convert(
            helper: com.chad.library.adapter.base.viewholder.BaseViewHolder,
            item: CommunityRecommend.Item
        ) {

        }

    }

    /**
     * banner布局
     */
    private inner class HorizontalScrollCardViewHolder(val fragment: RecommendFragment) :
        BaseItemProvider<CommunityRecommend.Item>() {
        override val itemViewType: Int
            get() = HORIZONTAL_SCROLLCARD_TYPE
        override val layoutId: Int
            get() = R.layout.item_community_horizontal_scrollcard_type

        override fun convert(
            helper: com.chad.library.adapter.base.viewholder.BaseViewHolder,
            item: CommunityRecommend.Item
        ) {
            (helper.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan =
                true
            helper.getView<BannerViewPager<CommunityRecommend.ItemX, BannerAdapter.ViewHolder>>(R.id.bannerViewPager)
                .run {
                    setCanLoop(false)
                    setRoundCorner(dp2px(4f))
                    setRevealWidth(0, GlobalUtil.getDimension(R.dimen.listSpaceSize))
                    if (item.data.itemList.size == 1) setPageMargin(0) else setPageMargin(dp2px(4f))
                    setIndicatorVisibility(View.GONE)
                    removeDefaultPageTransformer()
                    adapter = BannerAdapter()
                    setOnPageClickListener { position ->
                        ActionUrl.process(
                            fragment,
                            item.data.itemList[position].data.actionUrl,
                            item.data.itemList[position].data.title
                        )
                    }
                    create(item.data.itemList)
                }
        }
    }

    /**
     * 瀑布流布局
     */
    private inner class FollowCardViewHolder(val fragment: RecommendFragment) :
        BaseItemProvider<CommunityRecommend.Item>() {
        override val itemViewType: Int
            get() = FOLLOW_CARD_TYPE
        override val layoutId: Int
            get() = R.layout.item_community_columns_card_follow_card_type

        override fun convert(
            helper: com.chad.library.adapter.base.viewholder.BaseViewHolder,
            item: CommunityRecommend.Item
        ) {
            helper.setGone(R.id.tvChoiceness, true)
            helper.setGone(R.id.ivPlay, true)
            helper.setGone(R.id.ivLayers, true)

            if (item.data.content.data.library == DailyAdapter.DAILY_LIBRARY_TYPE)
                helper.setVisible(R.id.tvChoiceness, true)

            if (item.data.header?.iconType ?: "".trim() == "round") {
                helper.setGone(R.id.ivAvatar, true)
                helper.setVisible(R.id.ivRoundAvatar, true)
                helper.getView<ImageView>(R.id.ivRoundAvatar)
                    .load(item.data.content.data.owner.avatar)

            } else {
                helper.setGone(R.id.ivRoundAvatar, true)
                helper.setVisible(R.id.ivAvatar, true)
                helper.getView<ImageView>(R.id.ivAvatar).load(item.data.content.data.owner.avatar)
            }

            //TODO
            helper.getView<ImageView>(R.id.ivBgPicture).run {
                val imageHeight = calculateImageHeight(
                    item.data.content.data.width,
                    item.data.content.data.height
                )
                layoutParams.width = fragment.maxImageWidth
                layoutParams.height = imageHeight
                this.load(item.data.content.data.cover.feed, 4f)
            }

            helper.setText(
                R.id.tvCollectionCount,
                item.data.content.data.consumption.collectionCount.toString()
            )
            val drawable = ContextCompat.getDrawable(
                helper.itemView.context,
                R.drawable.ic_favorite_border_black_20dp
            )
            helper.getView<TextView>(R.id.tvCollectionCount).setDrawable(drawable, 17f, 17f, 2)
            helper.setText(R.id.tvDescription, item.data.content.data.description)
            helper.setText(R.id.tvNickName, item.data.content.data.owner.nickname)

            //TODO 可能是图片可能是视频
            when (item.data.content.type) {
                STR_VIDEO_TYPE -> {
                    helper.setVisible(R.id.ivPlay, true)
                    helper.itemView.setOnClickListener {
                        val items =
                            data.filter { it.type == STR_COMMUNITY_COLUMNS_CARD && it.data.dataType == STR_FOLLOW_CARD_DATA_TYPE }
                        fragment.activity?.let { it1 -> UgcDetailActivity.start(it1, items, item) }
                    }
                }
                STR_UGC_PICTURE_TYPE -> {
                    if (!item.data.content.data.urls.isNullOrEmpty() && item.data.content.data.urls.size > 1) helper.setVisible(
                        R.id.ivLayers,
                        true
                    )
                    helper.itemView.setOnClickListener {
                        val items =
                            data.filter { it.type == STR_COMMUNITY_COLUMNS_CARD && it.data.dataType == STR_FOLLOW_CARD_DATA_TYPE }
                        fragment.activity?.let { it1 -> UgcDetailActivity.start(it1, items, item) }
                    }
                }
                else -> {

                }
            }
        }

    }


    /**
     * 根据屏幕比例计算图片高
     *
     * @param originalWidth   服务器图片原始尺寸：宽
     * @param originalHeight  服务器图片原始尺寸：高
     * @return 根据比例缩放后的图片高
     */
    private fun calculateImageHeight(originalWidth: Int, originalHeight: Int): Int {
        //服务器数据异常处理
        if (originalWidth == 0 || originalHeight == 0) {
            Log.d(TAG, GlobalUtil.getString(R.string.image_size_error))
            return fragment.maxImageWidth
        }
        return fragment.maxImageWidth * originalHeight / originalWidth
    }

    inner class BannerAdapter :
        BaseBannerAdapter<CommunityRecommend.ItemX, BannerAdapter.ViewHolder>() {

        inner class ViewHolder(val view: View) : BaseViewHolder<CommunityRecommend.ItemX>(view) {

            override fun bindData(item: CommunityRecommend.ItemX, position: Int, pageSize: Int) {
                val ivPicture = findView<ImageView>(R.id.ivPicture)
                val tvLabel = findView<TextView>(R.id.tvLabel)
                if (item.data.label?.text.isNullOrEmpty()) tvLabel.invisible() else tvLabel.visible()
                tvLabel.text = item.data.label?.text ?: ""
                ivPicture.load(item.data.image, 4f)
            }
        }

        override fun getLayoutId(viewType: Int): Int {
            return R.layout.item_banner_item_type
        }

        override fun createViewHolder(view: View, viewType: Int): ViewHolder {
            return ViewHolder(view)
        }

        override fun onBind(
            holder: ViewHolder,
            data: CommunityRecommend.ItemX,
            position: Int,
            pageSize: Int
        ) {
            holder.bindData(data, position, pageSize)
        }
    }

    companion object {
        const val TAG = "CommendAdapter"

        const val STR_HORIZONTAL_SCROLLCARD_TYPE = "horizontalScrollCard"
        const val STR_COMMUNITY_COLUMNS_CARD = "communityColumnsCard"

        const val STR_HORIZONTAL_SCROLLCARD_DATA_TYPE = "HorizontalScrollCard"
        const val STR_ITEM_COLLECTION_DATA_TYPE = "ItemCollection"
        const val STR_FOLLOW_CARD_DATA_TYPE = "FollowCard"

        const val STR_VIDEO_TYPE = "video"
        const val STR_UGC_PICTURE_TYPE = "ugcPicture"

        const val HORIZONTAL_SCROLLCARD_ITEM_COLLECTION_TYPE =
            1   //type:horizontalScrollCard -> dataType:ItemCollection
        const val HORIZONTAL_SCROLLCARD_TYPE =
            2                   //type:horizontalScrollCard -> dataType:HorizontalScrollCard
        const val FOLLOW_CARD_TYPE =
            3                             //type:communityColumnsCard -> dataType:FollowCard
    }


}