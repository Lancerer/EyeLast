package com.lancer.eyelast.demo.player

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.lancer.eyelast.R
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.activity_simple.*

/**
 * GSY简单播放，包含全屏，视频title等基本功能
 */
class SimpleActivity : AppCompatActivity() {
    private var orientationUtils: OrientationUtils? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)
        init()
    }

    private fun init() {
        val source1 =
            "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
        //播放方法(视频源，是否缓存播放，视频名称)
        videoPlayer.setUp(source1, true, "sample")

        //添加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(R.drawable.user_login_bg1)
        videoPlayer.thumbImageView = imageView

        //增加标题
        videoPlayer.titleTextView.visibility = View.VISIBLE
        //设置返回键
        videoPlayer.backButton.visibility = View.VISIBLE
        videoPlayer.backButton.setOnClickListener { onBackPressed() }
        //设置旋转
        orientationUtils = OrientationUtils(this, videoPlayer)
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true)
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.fullscreenButton
            .setOnClickListener { orientationUtils?.resolveByClick() }

        videoPlayer.startPlayLogic()
    }

    override fun onResume() {
        super.onResume()
        videoPlayer.onVideoResume()
    }

    override fun onPause() {
        super.onPause()
        videoPlayer.onVideoPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        if (orientationUtils != null) {
            orientationUtils?.releaseListener()
        }
    }

    override fun onBackPressed() {
        //先返回正常状态
        if (orientationUtils?.screenType == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.fullscreenButton.performClick()
            return
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null)
        super.onBackPressed()
    }
}