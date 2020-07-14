package com.lancer.eyelast.ui.fragment.home.discovery

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lancer.eyelast.Const
import com.lancer.eyelast.R
import com.lancer.eyelast.bean.Discovery
import com.lancer.eyelast.extension.*
import com.lancer.eyelast.ui.activity.login.LoginActivity
import com.lancer.eyelast.ui.activity.video.VideoActivity
import com.lancer.eyelast.ui.activity.video.VideoActivity.Companion.start
import com.lancer.eyelast.ui.fragment.home.commend.CommendAdapter
import com.lancer.eyelast.ui.fragment.home.daily.DailyAdapter
import com.lancer.eyelast.utils.ActionUrl
import com.lancer.eyelast.utils.GlobalUtil
import com.lancer.eyelast.widget.CoverVideoPlayer
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * @author lancer
 * @des
 * @Date 2020/7/8 14:13
 */
class DiscoveryAdapter(fragment: DiscoveryFragment) : BaseProviderMultiAdapter<Discovery.Item>() {
    init {
        addItemProvider(TextCardViewHeader5ViewHolder(fragment))
        addItemProvider(TextCardViewHeader7ViewHolder(fragment))
        addItemProvider(TextCardViewHeader8ViewHolder(fragment))
        addItemProvider(TextCardViewFooter2ViewHolder(fragment))
        addItemProvider(TextCardViewFooter3ViewHolder(fragment))
        addItemProvider(FollowCardViewHolder(fragment))
        addItemProvider(HorizontalScrollCardViewHolder(fragment))
        addItemProvider(SpecialSquareCardCollectionViewHolder(fragment))
        addItemProvider(ColumnCardListViewHolder(fragment))
        addItemProvider(BannerViewHolder(fragment))
        addItemProvider(Banner3ViewHolder(fragment))
        addItemProvider(VideoSmallCardViewHolder(fragment))
        addItemProvider(TagBriefCardViewHolder(fragment))
        addItemProvider(TopicBriefCardViewHolder(fragment))
        addItemProvider(AutoPlayVideoAdViewHolder(fragment))
    }

    override fun getItemType(data: List<Discovery.Item>, position: Int): Int {
        return RecyclerViewHelp.getItemViewType(data[position])
    }


    private inner class TextCardViewHeader5ViewHolder(val fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.TEXT_CARD_HEADER5
        override val layoutId: Int
            get() = R.layout.item_text_card_type_header_five

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            helper.setText(R.id.tvTitle5, item.data.text)

            if (item.data.actionUrl != null) helper.setVisible(R.id.ivInto5, true)
            else helper.setVisible(R.id.ivInto5, false)

            if (item.data.follow != null) helper.setVisible(R.id.tvFollow, true)
            else helper.setVisible(R.id.tvFollow, false)

            val follow = helper.getView<TextView>(R.id.tvFollow)
            follow.setOnClickListener {
                fragment.activity?.let { it1 -> LoginActivity.start(it1) }
            }
            helper.getView<TextView>(R.id.tvTitle5).setOnClickListener {
                ActionUrl.process(fragment, item.data.actionUrl, item.data.text)
            }
            helper.getView<ImageView>(R.id.ivInto5).setOnClickListener {
                ActionUrl.process(fragment, item.data.actionUrl, item.data.text)
            }
        }
    }

    private inner class TextCardViewHeader7ViewHolder(val fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.TEXT_CARD_HEADER7
        override val layoutId: Int
            get() = R.layout.item_text_card_type_header_seven

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            helper.setText(R.id.tvTitle7, item.data.text)
            helper.setText(R.id.tvRightText7, item.data.rightText)
            helper.getView<TextView>(R.id.tvRightText7).setOnClickListener {
                ActionUrl.process(
                    fragment,
                    item.data.actionUrl,
                    "${item.data.text},${item.data.rightText}"
                )
            }
            helper.getView<ImageView>(R.id.ivInto7).setOnClickListener {
                ActionUrl.process(
                    fragment,
                    item.data.actionUrl,
                    "${item.data.text},${item.data.rightText}"
                )
            }
        }

    }

    private inner class TextCardViewHeader8ViewHolder(val fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.TEXT_CARD_HEADER8
        override val layoutId: Int
            get() = R.layout.item_text_card_type_header_eight

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            helper.setText(R.id.tvTitle8, item.data.text)
            helper.setText(R.id.tvRightText8, item.data.rightText)
            helper.getView<TextView>(R.id.tvRightText8).setOnClickListener {
                ActionUrl.process(fragment, item.data.actionUrl, item.data.text)
            }
            helper.getView<ImageView>(R.id.ivInto8).setOnClickListener {
                ActionUrl.process(fragment, item.data.actionUrl, item.data.text)
            }
        }
    }

    private inner class TextCardViewFooter2ViewHolder(val fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.TEXT_CARD_FOOTER2
        override val layoutId: Int
            get() = R.layout.item_text_card_type_footer_two

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            helper.setText(R.id.tvFooterRightText2, item.data.text)
            helper.getView<TextView>(R.id.tvFooterRightText2).setOnClickListener {
                ActionUrl.process(fragment, item.data.actionUrl, item.data.text)
            }
            helper.getView<ImageView>(R.id.ivTooterInto2).setOnClickListener {
                ActionUrl.process(fragment, item.data.actionUrl, item.data.text)
            }
        }
    }

    private inner class TextCardViewFooter3ViewHolder(val fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.TEXT_CARD_FOOTER3
        override val layoutId: Int
            get() = R.layout.item_text_card_type_footer_three

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            helper.setText(R.id.tvFooterRightText3, item.data.text)
            //TODO
        }
    }

    private inner class FollowCardViewHolder(val fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.FOLLOW_CARD
        override val layoutId: Int
            get() = R.layout.item_follow_card_type

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            val ivVideo = helper.getView<ImageView>(R.id.ivVideo)
            val ivAvatar = helper.getView<ImageView>(R.id.ivAvatar)
            ivVideo.load(item.data.content.data.cover.feed, 4f)
            ivAvatar.load(item.data.header.icon, 4f)

            helper.setText(
                R.id.tvVideoDuration,
                item.data.content.data.duration.conversionVideoDuration()
            )
            helper.setText(
                R.id.tvDescription,
                item.data.content.data.duration.conversionVideoDuration()
            )
            helper.setText(R.id.tvTitle, item.data.header.title)
            if (item.data.content.data.library == DailyAdapter.DAILY_LIBRARY_TYPE)
                helper.setVisible(R.id.ivChoiceness, true) else helper.setGone(
                R.id.ivChoiceness,
                true
            )
            helper.itemView.setOnClickListener {
                item.data.content.data.run {
                    if (ad || author == null) {
                        fragment.activity?.let { it1 -> VideoActivity.start(it1, id) }
                    } else {
                        fragment.activity?.let { it1 ->
                            VideoActivity.start(
                                it1,
                                VideoActivity.VideoInfo(
                                    id,
                                    playUrl,
                                    title,
                                    description,
                                    category,
                                    library,
                                    consumption,
                                    cover,
                                    author,
                                    webUrl
                                )
                            )
                        }
                    }
                }
            }
        }
    }


    private inner class HorizontalScrollCardViewHolder(val fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.HORIZONTAL_SCROLL_CARD
        override val layoutId: Int
            get() = R.layout.item_horizontal_scroll_card_type

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            val bannerViewPager =
                helper.getView<BannerViewPager<Discovery.ItemX, DiscoveryAdapter.HorizontalScrollCardAdapter.ViewHolder>
                        >(R.id.bannerViewPager)

            bannerViewPager.run {
                setCanLoop(false)
                setRoundCorner(dp2px(4f))
                setRevealWidth(GlobalUtil.getDimension(R.dimen.listSpaceSize))
                if (item.data.itemList.size == 1) setPageMargin(0) else setPageMargin(dp2px(4f))
                setIndicatorVisibility(View.GONE)
                adapter = HorizontalScrollCardAdapter()
                removeDefaultPageTransformer()
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

    private inner class SpecialSquareCardCollectionViewHolder(val fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.SPECIAL_SQUARE_CARD_COLLECTION
        override val layoutId: Int
            get() = R.layout.item_special_square_card_collection_type

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            helper.setText(R.id.tvTitle, item.data.header.title)
            helper.setText(R.id.tvRightText, item.data.header.rightText)
            val recyclerView: RecyclerView = helper.getView(R.id.recyclerView)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(fragment.activity, 2).apply {
                orientation = GridLayoutManager.HORIZONTAL
            }
            val list =
                item.data.itemList.filter { it.type == "squareCardOfCategory" && it.data.dataType == "SquareCard" }
            recyclerView.adapter = SpecialSquareCardCollectionAdapter(list)
        }
    }

    private inner class ColumnCardListViewHolder(val fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.COLUMN_CARD_LIST
        override val layoutId: Int
            get() = R.layout.item_column_card_list_type

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            helper.setText(R.id.tvTitle, item.data.header.title)
            helper.setText(R.id.tvRightText, item.data.header.rightText)
            val recyclerView: RecyclerView = helper.getView(R.id.recyclerView)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(fragment.activity, 2)

            val list =
                item.data.itemList.filter { it.type == "squareCardOfColumn" && it.data.dataType == "SquareCard" }
            recyclerView.adapter = ColumnCardListAdapter(list)
        }
    }

    private inner class BannerViewHolder(val fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.BANNER
        override val layoutId: Int
            get() = R.layout.item_banner_type

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            val ivPicture: ImageView = helper.getView(R.id.ivPicture)
            ivPicture.load(item.data.image, 4f)
            helper.itemView.setOnClickListener {
                ActionUrl.process(fragment, item.data.actionUrl, item.data.title)
            }
        }
    }

    private inner class Banner3ViewHolder(val fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.BANNER3
        override val layoutId: Int
            get() = R.layout.item_banner_three_type

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            val ivPicture = helper.getView<ImageView>(R.id.ivPicture)
            val ivAvatar = helper.getView<ImageView>(R.id.ivAvatar)
            ivPicture.load(item.data.image, 4f)
            ivAvatar.load(item.data.header.icon)

            helper.setText(R.id.tvTitle, item.data.header.title)
            helper.setText(R.id.tvDescription, item.data.header.description)
            //todo
            helper.setText(R.id.tvLabel, item.data.label?.text ?: "")
            helper.itemView.setOnClickListener {
                ActionUrl.process(
                    fragment,
                    item.data.actionUrl,
                    item.data.header.title
                )
            }

        }
    }

    private inner class VideoSmallCardViewHolder(val fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.VIDEO_SMALL_CARD
        override val layoutId: Int
            get() = R.layout.item_video_small_card_type

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            val ivPicture = helper.getView<ImageView>(R.id.ivPicture)

            ivPicture.load(item.data.cover.feed, 4f)
            helper.setText(
                R.id.tvDescription,
                if (item.data.library == DailyAdapter.DAILY_LIBRARY_TYPE) "#${item.data.category} / 开眼精选" else "#${item.data.category}"
            )
            helper.setText(R.id.tvTitle, item.data.title)
            helper.setText(R.id.tvVideoDuration, item.data.duration.conversionVideoDuration())
            helper.itemView.setOnClickListener {
                item.data.run {
                    fragment.activity?.let { it1 ->
                        VideoActivity.start(
                            it1,
                            VideoActivity.VideoInfo(
                                id,
                                playUrl,
                                title,
                                description,
                                category,
                                library,
                                consumption,
                                cover,
                                author,
                                webUrl
                            )
                        )
                    }
                }
            }
        }
    }

    private inner class TagBriefCardViewHolder(fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.TAG_BRIEFCARD
        override val layoutId: Int
            get() = R.layout.item_brief_card_tag_brief_card_type

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            val ivPicture = helper.getView<ImageView>(R.id.ivPicture)
            ivPicture.load(item.data.icon, 4f)
            helper.setText(R.id.tvDescription, item.data.description)
            helper.setText(R.id.tvTitle, item.data.title)

        }
    }

    private inner class TopicBriefCardViewHolder(fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.TOPIC_BRIEFCARD
        override val layoutId: Int
            get() = R.layout.item_brief_card_topic_brief_card_type

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            val ivPicture = helper.getView<ImageView>(R.id.ivPicture)
            ivPicture.load(item.data.icon, 4f)
            helper.setText(R.id.tvDescription, item.data.description)
            helper.setText(R.id.tvTitle, item.data.title)

        }
    }

    private inner class AutoPlayVideoAdViewHolder(val fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.AUTO_PLAY_VIDEO_AD
        override val layoutId: Int
            get() = R.layout.item_auto_play_video_ad

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            item.data.detail?.run {
                val ivAvatar = helper.getView<ImageView>(R.id.ivAvatar)
                ivAvatar.load(item.data.detail.icon)
                helper.setText(R.id.tvDescription, item.data.detail.description)
                helper.setText(R.id.tvTitle, item.data.detail.title)
                fragment.activity?.let {
                    CommendAdapter.startAutoPlay(
                        it,
                        helper.getView(R.id.videoPlayer),
                        position,
                        url,
                        imageUrl,
                        CommendAdapter.TAG,
                        object : GSYSampleCallBack() {
                            override fun onPrepared(url: String?, vararg objects: Any?) {
                                super.onPrepared(url, *objects)
                                GSYVideoManager.instance().isNeedMute = true
                            }

                            override fun onClickBlank(url: String?, vararg objects: Any?) {
                                super.onClickBlank(url, *objects)
                                ActionUrl.process(fragment, item.data.detail.actionUrl)
                            }
                        })
                    helper.itemView.setOnClickListener {
                        ActionUrl.process(fragment, item.data.detail.actionUrl)
                    }
                }

            }
        }
    }


    inner class HorizontalScrollCardAdapter :
        BaseBannerAdapter<Discovery.ItemX, HorizontalScrollCardAdapter.ViewHolder>() {

        inner class ViewHolder(val view: View) :
            com.zhpan.bannerview.BaseViewHolder<Discovery.ItemX>(view) {

            override fun bindData(item: Discovery.ItemX, position: Int, pageSize: Int) {
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
            data: Discovery.ItemX,
            position: Int,
            pageSize: Int
        ) {
            holder.bindData(data, position, pageSize)
        }
    }

    inner class SpecialSquareCardCollectionAdapter(val dataList: List<Discovery.ItemX>) :
        RecyclerView.Adapter<SpecialSquareCardCollectionAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val ivPicture = view.findViewById<ImageView>(R.id.ivPicture)
            val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): SpecialSquareCardCollectionAdapter.ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_special_square_card_collection_type_item, parent, false)
            )
        }

        override fun getItemCount() = dataList.size

        override fun onBindViewHolder(
            holder: SpecialSquareCardCollectionAdapter.ViewHolder,
            position: Int
        ) {
            val item = dataList[position]
            holder.ivPicture.load(item.data.image, 4f)
            holder.tvTitle.text = item.data.title
            holder.itemView.setOnClickListener { "${item.data.title},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast() }
        }
    }

    inner class ColumnCardListAdapter(val dataList: List<Discovery.ItemX>) :
        RecyclerView.Adapter<ColumnCardListAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val ivPicture = view.findViewById<ImageView>(R.id.ivPicture)
            val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ColumnCardListAdapter.ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_column_card_list_type_item, parent, false)
            )
        }

        override fun getItemCount() = dataList.size

        override fun onBindViewHolder(holder: ColumnCardListAdapter.ViewHolder, position: Int) {
            val item = dataList[position]
            holder.ivPicture.load(item.data.image, 4f)
            holder.tvTitle.text = item.data.title
            holder.itemView.setOnClickListener { "${item.data.title},${GlobalUtil.getString(R.string.currently_not_supported)}".showToast() }
        }
    }

    inner class SpecialSquareCardCollectionItemDecoration : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val count = parent.adapter?.itemCount //item count
            val spanIndex = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
            val spanCount = 2
            val lastRowFirstItemPostion = count?.minus(spanCount)   //最后一行,第一个item索引
            val space = dp2px(2f)
            val rightCountSpace = dp2px(14f)

            when (spanIndex) {
                0 -> {
                    outRect.bottom = space
                }
                spanCount - 1 -> {
                    outRect.top = space
                }
                else -> {
                    outRect.top = space
                    outRect.bottom = space
                }
            }
            when {
                position < spanCount -> {
                    outRect.right = space
                }
                position < lastRowFirstItemPostion!! -> {
                    outRect.left = space
                    outRect.right = space
                }
                else -> {
                    outRect.left = space
                    outRect.right = rightCountSpace
                }
            }
        }
    }

    companion object {
        const val TAG = "DiscoveryAdapter"
    }
}