package com.lancer.eyelast.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lancer.eyelast.R
import com.lancer.eyelast.extension.gone
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView

/**
 * @author lancer
 * @des
 * @Date 2020/7/7 9:43
 */
class CoverVideoPlayer : StandardGSYVideoPlayer {

    /**
     *  是否第一次加载视频。用于隐藏进度条、播放按钮等UI。播放完成后，重新加载视频，会重置为true。
     */
    private var initFirstLoad = true

    lateinit var mCoverImage: ImageView
    lateinit var mCoverOriginUrl: String

    var mCoverOriginId = 0

    var mDefaultRes = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, fullFlag: Boolean?) : super(context, fullFlag)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId() = R.layout.layout_new_detail_video_player

    override fun init(context: Context?) {
        super.init(context)
        mCoverImage = findViewById(R.id.thumbImage)
        if (mThumbImageViewLayout != null &&
            (mCurrentState == -1 || mCurrentState == CURRENT_STATE_NORMAL || mCurrentState == CURRENT_STATE_ERROR)
        ) {
            mThumbImageViewLayout.visibility = View.VISIBLE
        }
    }

    /**
     * 加载封面图方法
     */
    fun loadCoverImage(url: String, res: Int) {
        mCoverOriginUrl = url
        mDefaultRes = res
        Glide.with(context.applicationContext)
            .setDefaultRequestOptions(
                RequestOptions()
                    .frame(1000000)
                    .centerCrop().error(res)
                    .placeholder(res)
            )
            .load(url)
            .into(mCoverImage)
    }

    fun loadCoverImageBy(id: Int, res: Int) {
        mCoverOriginId = id
        mDefaultRes = res
        mCoverImage.setImageResource(id)
    }

    override fun startWindowFullscreen(
        context: Context?,
        actionBar: Boolean,
        statusBar: Boolean
    ): GSYBaseVideoPlayer {
        val gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar)
        val coverVideoPlayer = gsyBaseVideoPlayer as CoverVideoPlayer
        if (mCoverOriginUrl != null) {
            coverVideoPlayer.loadCoverImage(mCoverOriginUrl, mDefaultRes);
        } else if (mCoverOriginId != 0) {
            coverVideoPlayer.loadCoverImageBy(mCoverOriginId, mDefaultRes);
        }
        return gsyBaseVideoPlayer
    }


    override fun updateStartImage() {
        if (mStartButton is ImageView) {
            val imageView = mStartButton as ImageView
            when (mCurrentState) {
                GSYVideoView.CURRENT_STATE_PLAYING -> {
                    imageView.setImageResource(R.drawable.ic_pause_white_24dp)
                    imageView.setBackgroundResource(R.drawable.sel_pause_white_bg)
                }
                GSYVideoView.CURRENT_STATE_ERROR -> {
                    imageView.setImageResource(R.drawable.ic_play_white_24dp)
                    imageView.setBackgroundResource(R.drawable.sel_play_white_bg)
                }
                GSYVideoView.CURRENT_STATE_AUTO_COMPLETE -> {
                    imageView.setImageResource(R.drawable.ic_refresh_white_24dp)
                    imageView.setBackgroundResource(0)
                }
                else -> {
                    imageView.setImageResource(R.drawable.ic_play_white_24dp)
                    imageView.setBackgroundResource(R.drawable.sel_play_white_bg)
                }
            }

        } else {
            super.updateStartImage()
        }
    }

    //正常
    override fun changeUiToNormal() {
        super.changeUiToNormal()
        Log.d(javaClass.simpleName, "changeUiToNormal")
        initFirstLoad = true
    }
    //准备中
    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
        Log.d(javaClass.simpleName, "changeUiToPreparingShow")
        mBottomContainer.gone()
        mStartButton.gone()
    }

    //播放中
    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
        Log.d(javaClass.simpleName, "changeUiToPlayingShow")
        if (initFirstLoad) {
            mBottomContainer.gone()
            mStartButton.gone()
        }
        initFirstLoad = false
    }

    //开始缓冲
    override fun changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow()
        Log.d(javaClass.simpleName, "changeUiToPlayingBufferingShow")
    }

    //暂停
    override fun changeUiToPauseShow() {
        super.changeUiToPauseShow()
        Log.d(javaClass.simpleName, "changeUiToPauseShow")
    }

    //自动播放结束
    override fun changeUiToCompleteShow() {
        super.changeUiToCompleteShow()
        Log.d(javaClass.simpleName, "changeUiToCompleteShow")
        mBottomContainer.gone()
    }

    //错误状态
    override fun changeUiToError() {
        super.changeUiToError()
        Log.d(javaClass.simpleName, "changeUiToError")
    }

    fun getBottomContainer() = mBottomContainer

}