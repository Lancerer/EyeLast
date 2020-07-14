package com.lancer.eyelast.ui.activity.ugc

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseActivity
import com.lancer.eyelast.bean.CommunityRecommend
import com.lancer.eyelast.databinding.ActivityUgcDetailBinding
import com.lancer.eyelast.extension.showToast
import com.lancer.eyelast.ui.activity.ugc.callback.AutoPlayPageChangeListener
import com.lancer.eyelast.utils.GlobalUtil
import com.lancer.eyelast.utils.IntentDataHolderUtil
import com.shuyu.gsyvideoplayer.GSYVideoManager

class UgcDetailActivity : BaseActivity<ActivityUgcDetailBinding>() {
    companion object {
        const val TAG = "UgcDetailActivity"
        private const val EXTRA_RECOMMEND_ITEM_LIST_JSON = "recommend_item_list"
        private const val EXTRA_RECOMMEND_ITEM_JSON = "recommend_item"

        fun start(
            context: Activity,
            dataList: List<CommunityRecommend.Item>,
            currentItem: CommunityRecommend.Item
        ) {
            IntentDataHolderUtil.setData(EXTRA_RECOMMEND_ITEM_LIST_JSON, dataList)
            IntentDataHolderUtil.setData(EXTRA_RECOMMEND_ITEM_JSON, currentItem)
            val starter = Intent(context, UgcDetailActivity::class.java)
            context.startActivity(starter)
            context.overridePendingTransition(R.anim.anl_push_bottom_in, R.anim.anl_push_up_out)
        }
    }

    var dataList: List<CommunityRecommend.Item>? = null

    var itemPosition: Int = -1

    private lateinit var adapter: UgcAdapter

    override fun initView() {
    }

    override fun initData() {
        if (dataList == null) {
            itemPosition = getCurrentItemPosition()
            dataList = IntentDataHolderUtil.getData<List<CommunityRecommend.Item>>(
                EXTRA_RECOMMEND_ITEM_LIST_JSON
            )
        }
        if (dataList == null) {
            GlobalUtil.getString(R.string.jump_page_unknown_error).showToast()
            finish()
        } else {
            adapter = UgcAdapter(this, dataList!!)
            binding.viewPager.adapter = adapter
            binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
            binding.viewPager.offscreenPageLimit = 1
            binding.viewPager.registerOnPageChangeCallback(
                AutoPlayPageChangeListener(
                    binding.viewPager,
                    itemPosition,
                    R.id.videoPlayer
                )
            )
            binding.viewPager.setCurrentItem(itemPosition, false)
        }

    }

    private fun checkArguments() = if (IntentDataHolderUtil.getData<List<CommunityRecommend.Item>>(
            EXTRA_RECOMMEND_ITEM_LIST_JSON
        ).isNullOrEmpty()
        || IntentDataHolderUtil.getData<CommunityRecommend.Item>(EXTRA_RECOMMEND_ITEM_JSON) == null
    ) {
        GlobalUtil.getString(R.string.jump_page_unknown_error).showToast()
        finish()
        false
    } else {
        true
    }

    private fun getCurrentItemPosition(): Int {
        val list = IntentDataHolderUtil.getData<List<CommunityRecommend.Item>>(
            EXTRA_RECOMMEND_ITEM_LIST_JSON
        )
        val currentItem =
            IntentDataHolderUtil.getData<CommunityRecommend.Item>(EXTRA_RECOMMEND_ITEM_JSON)
        list?.forEachIndexed { index, item ->
            if (currentItem == item) {
                itemPosition = index
                return@forEachIndexed
            }
        }
        return itemPosition
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.anl_push_bottom_out)
    }

    override fun initLayout(): Int = R.layout.activity_ugc_detail
}