package com.lancer.eyelast.demo.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lancer.eyelast.R
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.activity_empty.*

class EmptyActivity : AppCompatActivity() {


    private var orientationUtils: OrientationUtils? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)
        init()
    }

    private fun init() {
        val url = "https://res.exexm.com/cw_145225549855002"
        orientationUtils = OrientationUtils(this, emptyPlayer)
        emptyPlayer.setUp(url, false, "")
        emptyPlayer.startPlayLogic()
    }

    override fun onDestroy() {
        super.onDestroy()
        emptyPlayer.release()
        if (orientationUtils != null) {
            orientationUtils?.releaseListener()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        emptyPlayer.setVideoAllCallBack(null)
        GSYVideoManager.releaseAllVideos()
    }
}