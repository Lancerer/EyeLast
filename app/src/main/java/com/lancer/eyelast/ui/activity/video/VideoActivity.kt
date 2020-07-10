package com.lancer.eyelast.ui.activity.video

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseActivity
import com.lancer.eyelast.bean.*
import com.lancer.eyelast.databinding.ActivityVideoBinding
import com.lancer.eyelast.extension.gone
import com.lancer.eyelast.extension.load
import com.lancer.eyelast.extension.visible
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener
import com.lancer.eyelast.utils.InjectorUtil
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import kotlinx.android.parcel.Parcelize

class VideoActivity : BaseActivity<ActivityVideoBinding>() {

    private var orientationUtils: OrientationUtils? = null

    private lateinit var mDetailCommentListAdapter: VideoDetailCommentListAdapter
    private lateinit var mRecommendListAdapter: VideoRecommendListAdapter
    private lateinit var mergeAdapter: MergeAdapter


    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getVideoViewModelFactory())
            .get(VideoViewModel::class.java)
    }

    private var nextPageUrl: String? = null

    private val commentListener: CommendListener = CommendListener()
    private val recommendListener: RecommendListener = RecommendListener()


    override fun initView() {
        initParams()
        orientationUtils = OrientationUtils(this, binding.videoPlayer)
        startVideoPlayer()
        mDetailCommentListAdapter = VideoDetailCommentListAdapter(this, viewModel.videoInfoData)
        mRecommendListAdapter = VideoRecommendListAdapter(this, viewModel.videoInfoData)
        mergeAdapter = MergeAdapter(mDetailCommentListAdapter, mRecommendListAdapter)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = mergeAdapter
        binding.recyclerView.setHasFixedSize(true)

    }

    override fun initData() {
    }

    private fun initParams() {
        if (intent.getParcelableExtra<VideoInfo>(EXTRA_VIDEOINFO) != null)
            viewModel.videoInfoData = intent.getParcelableExtra(EXTRA_VIDEOINFO)
        if (intent.getLongExtra(EXTRA_VIDEO_ID, 0L) != 0L)
            viewModel.videoId = intent.getLongExtra(EXTRA_VIDEO_ID, 0L)
    }

    /**
     * 播放
     */
    private fun startVideoPlayer() {
        viewModel.videoInfoData?.run {
            binding.ivBlurredBg.load(cover.blurred)
            binding.tvReplyCount.text = consumption.replyCount.toString()
            binding.videoPlayer.startPlay()
        }
    }

    inner class VideoCallPlayBack : GSYSampleCallBack() {
        override fun onStartPrepared(url: String?, vararg objects: Any?) {
            super.onStartPrepared(url, *objects)
            binding.flHeader.gone()
            binding.llShares.gone()
        }

        override fun onClickBlank(url: String?, vararg objects: Any?) {
            super.onClickBlank(url, *objects)
            // switchTitleBarVisible()
        }

        override fun onClickStop(url: String?, vararg objects: Any?) {
            super.onClickStop(url, *objects)
            // delayHideBottomContainer()
        }

        override fun onAutoComplete(url: String?, vararg objects: Any?) {
            super.onAutoComplete(url, *objects)
            binding.flHeader.visible()
            binding.ivPullDown.visible()
            binding.ivCollection.gone()
            binding.ivShare.gone()
            binding.ivMore.gone()
            binding.llShares.visible()
        }
    }

    private fun showFull() {
        orientationUtils?.run { if (isLand != 1) resolveByClick() }
        binding.videoPlayer.startWindowFullscreen(this, actionBar = true, statusBar = false)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        initParams()
        startVideoPlayer()
        // viewModel.onRefresh()

    }

    override fun onPause() {
        super.onPause()
        binding.videoPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        binding.videoPlayer.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        orientationUtils?.releaseListener()
        binding.videoPlayer.release()
        binding.videoPlayer.setVideoAllCallBack(null)
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(this)) return
        super.onBackPressed()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding.videoPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
    }

    override fun initLayout(): Int = R.layout.activity_video


    @Parcelize
    data class VideoInfo(
        val videoId: Long,
        val playUrl: String,
        val title: String,
        val description: String,
        val category: String,
        val library: String,
        val consumption: Consumption,
        val cover: Cover,
        val author: Author?,
        val webUrl: WebUrl
    ) : Parcelable

    /**
     * 拓展方法
     */
    private fun GSYVideoPlayer.startPlay() {
        viewModel.videoInfoData?.let {
            //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
            fullscreenButton.setOnClickListener { showFull() }
            //防止错位设置
            playTag = TAG
            //音频焦点冲突时是否释放
            isReleaseWhenLossAudio = false
            //增加封面
            val imageView = ImageView(this@VideoActivity)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.load(it.cover.detail)
            thumbImageView = imageView
            thumbImageView.setOnClickListener {
                //switchTitleBarVisible()
            }
            //是否开启自动旋转
            isRotateViewAuto = false
            //是否需要全屏锁定屏幕功能
            isNeedLockFull = true
            //是否可以滑动调整
            setIsTouchWiget(true)
            //设置触摸显示控制ui的消失时间
            dismissControlTime = 5000
            //设置播放过程中的回调
            setVideoAllCallBack(VideoCallPlayBack())
            //设置播放URL
            setUp(it.playUrl, false, it.title)
            //开始播放
            startPlayLogic()
        }
    }

    private inner class CommendListener : OnNextWithErrorListener<VideoReplies> {
        override fun onNext(t: VideoReplies?) {

        }

        override fun onError(e: Throwable?) {

        }

    }

    private inner class RecommendListener : OnNextWithErrorListener<VideoRelated> {
        override fun onNext(t: VideoRelated?) {

        }

        override fun onError(e: Throwable?) {
        }

    }

    companion object {
        const val TAG = "NewDetailActivity"

        const val EXTRA_VIDEOINFO = "videoInfo"
        const val EXTRA_VIDEO_ID = "videoId"

        fun start(context: Activity, videoInfo: VideoInfo) {
            val starter = Intent(context, VideoActivity::class.java)
            starter.putExtra(EXTRA_VIDEOINFO, videoInfo)
            context.startActivity(starter)
            context.overridePendingTransition(R.anim.anl_push_bottom_in, R.anim.anl_push_up_out)
        }

        fun start(context: Activity, videoId: Long) {
            val starter = Intent(context, VideoActivity::class.java)
            starter.putExtra(EXTRA_VIDEO_ID, videoId)
            context.startActivity(starter)
            context.overridePendingTransition(R.anim.anl_push_bottom_in, R.anim.anl_push_up_out)
        }
    }
}