package com.lancer.eyelast.ui.fragment.mine

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lancer.eyelast.BaseApplication
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.databinding.FragmentMineBinding
import com.lancer.eyelast.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseFragment<FragmentMineBinding>(), View.OnClickListener {
    init {
        name = MineFragment::class.java.name
    }

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun initView() {
        binding.mineVersionTv.text = AppUtils.getVerName(BaseApplication.context)
        binding.mineMoreIv.setOnClickListener(this)
        binding.mineAvatarIv.setOnClickListener(this)
        binding.mineTipsTv.setOnClickListener(this)
        binding.mineFavoritesTv.setOnClickListener(this)
        binding.mineCacheTv.setOnClickListener(this)
        binding.mineFollowTv.setOnClickListener(this)
        binding.mineRecordTv.setOnClickListener(this)
        binding.mineNotificationToggleTv.setOnClickListener(this)
        binding.mineBadgeTv.setOnClickListener(this)
        binding.mineContributeTv.setOnClickListener(this)
        binding.mineFeedbackTv.setOnClickListener(this)
        binding.mineVersionTv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                R.id.mine_more_iv -> {
                    //查看更多
                }
                R.id.mine_version_tv -> {
                    //版本
                }
                R.id.mine_feedback_tv -> {
                    //反馈
                }
                R.id.mine_contribute_tv -> {
                    //投稿
                }
                R.id.mine_avatar_iv, R.id.mine_tips_tv, R.id.mine_favorites_tv, R.id.mine_cache_tv, R.id.mine_follow_tv, R.id.mine_record_tv, R.id.mine_notification_toggle_tv, R.id.mine_badge_tv -> {

                }
            }
        }

    }

    override fun initData() {
    }

    override fun initLayout(): Int = R.layout.fragment_mine


}