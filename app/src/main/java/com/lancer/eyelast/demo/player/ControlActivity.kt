package com.lancer.eyelast.demo.player

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.lancer.eyelast.R
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.activity_control.*

/**
 * 注意AndroidManifest文件中的config配置
 */
class ControlActivity : AppCompatActivity() {
    private var orientationUtils: OrientationUtils? = null

    private var isPlay = false
    private var isPause = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)
        init()
    }

    private fun init() {
        val url = "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4"

        //外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(this, controlPlayer)
        //初始化不打开外部的旋转
        orientationUtils?.isEnable = false

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(R.drawable.user_login_bg1)

        //title和返回按钮
        controlPlayer.titleTextView.visibility = View.GONE
        controlPlayer.backButton.visibility = View.GONE

        //player的配置
        val gsyOption = GSYVideoOptionBuilder()
        gsyOption.setThumbImageView(imageView)
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setAutoFullWithSize(false)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setUrl(url)
            .setCacheWithPlay(false)
            .setVideoTitle("测试视频")
            .setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String?, vararg objects: Any?) {
                    super.onPrepared(url, *objects)
                    //点击开始播放才旋转屏幕
                    orientationUtils?.isEnable = true
                    isPlay = true
                }

                override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                    super.onQuitFullscreen(url, *objects)
                    //退出全屏
                    orientationUtils?.backToProtVideo()
                }
            }).setLockClickListener { view, lock ->
                //锁屏监听
                if (orientationUtils != null) {
                    orientationUtils!!.isEnable = !lock
                }
            }.build(controlPlayer)

        //player全屏按钮
        controlPlayer.fullscreenButton.setOnClickListener {
            //点击切换的逻辑，比如竖屏的时候点击了就是切换到横屏不会受屏幕的影响
            orientationUtils!!.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusBar
            controlPlayer.startWindowFullscreen(
                this@ControlActivity,
                true,
                true
            )
        }
    }

    override fun onResume() {
        super.onResume()
        controlPlayer.onVideoResume(false)
        isPause = false
    }

    override fun onPause() {
        super.onPause()
        controlPlayer.onVideoPause()
        isPause = true
    }

    /**
     * 界面销毁逻辑处理
     */
    override fun onDestroy() {
        super.onDestroy()
        controlPlayer.release()
        if (orientationUtils != null) {
            orientationUtils?.releaseListener()
        }
    }

    /**
     * 物理返回键逻辑处理
     */
    override fun onBackPressed() {
        super.onBackPressed()
        if (orientationUtils != null) {
            orientationUtils!!.backToProtVideo()
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
    }

    /**
     * 屏幕方向变化逻辑处理
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            controlPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }
}