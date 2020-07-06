package com.lancer.eyelast.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseActivity
import com.lancer.eyelast.databinding.ActivityVideoBinding
import com.shuyu.gsyvideoplayer.utils.OrientationUtils

class VideoActivity : BaseActivity<ActivityVideoBinding>() {

    private var orientationUtils: OrientationUtils? = null

    override fun initView() {

    }

    override fun initData() {
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

    }

    override fun onPause() {
        super.onPause()
        binding.videoPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        binding.videoPlayer.onVideoResume()
    }
    override fun initLayout(): Int =R.layout.activity_video
}