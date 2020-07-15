package com.lancer.eyelast.demo.player

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lancer.eyelast.R
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import kotlinx.android.synthetic.main.activity_control.*
import kotlinx.android.synthetic.main.activity_empty.*
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        init()
    }

    /**
     * GSYVideoPlayer播放器内核：MediaPlayer，IjkMediaPlayer，ExoPlayer2 （IPlayerManager）
     *
     * Cache缓存层：ExoPlayerCacheManager  ProxyCacheManager （ICacheManager）
     *
     * Manager内核管理层  ：GSYVideoManager， GSYVideoManager继承GSYVideoBaseManager，通过IPlayerManager控制播放内核，实现GSYVideoViewBridge，和UI层交互
     *
     * ---------------------------------------------------------------
     * video播放器控件层：GSYTextureRenderView->GSYVideoView->GSYVideoControlView->GSYBaseVideoPlayer->GSYVideoPlayer->StandardGSYVideoPlayer
     * 继承关系就是上面箭头实现
     *
     * GSYTextureRenderView： 绘制View
     *
     * GSYVideoView： 视频回调与状态处理等相关层
     *
     * GSYVideoControlView： 处理播放UI的显示、控制层、手势处理等
     *
     * GSYBaseVideoPlayer：处理全屏和小屏幕逻辑
     *
     * GSYVideoPlayer：兼容的空View，目前用于 GSYVideoManager的设置
     *
     * StandardGSYVideoPlayer：封裝好的一个简单的播放器，可以直接使用，继承了前面所有父类的功能，也可以自定义其中布局实现不同功能的播放器：带控制的播放器，空实现的播放器，带封面的播放器，列表播放器
     *-------------------------------------------------------------------
     * Render 渲染控件层：TextureView、SurfaceView、GLSurfaceView
     *
     * OrientationUtils：处理屏幕旋转的的逻辑
     *
     *
     */
    private fun init() {

        sample.setOnClickListener {
            startActivity(Intent(this, SimpleActivity::class.java))

        }
        empty.setOnClickListener {
            startActivity(Intent(this, EmptyActivity::class.java))

        }
        control.setOnClickListener {
            startActivity(Intent(this, ControlActivity::class.java))
        }
    }
}