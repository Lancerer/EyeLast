package com.lancer.eyelast.demo.player.widget

import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lancer.eyelast.R
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer

/**
 * @author lancer
 * @des 带控制的video
 * @Date 2020/7/15 10:46
 */
class ControlVideoPlayer : StandardGSYVideoPlayer {
    private lateinit var mMoreScale: TextView

    private lateinit var mChangeRotate: TextView

    private lateinit var mChangeTransform: TextView

    //记住切换数据源类型
    private var mType = 0

    private var mTransformSize = 0

    //数据源
    private var mSourcePosition = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, fullFlag: Boolean) : super(context, fullFlag)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun init(context: Context?) {
        super.init(context)
        initView()
    }

    override fun getLayoutId(): Int = R.layout.player_control_video
    private fun initView() {
        //清晰度
        mMoreScale = findViewById(R.id.moreScale)
        //播放角度
        mChangeRotate = findViewById(R.id.change_rotate)
        //镜像旋转
        mChangeTransform = findViewById(R.id.change_transform)

        //切换清晰度
        mMoreScale.setOnClickListener {
            //没有播放过
            if (!mHadPlay) {
                return@setOnClickListener
            }
            when (mType) {
                0 -> {
                    mType = 1
                }
                1 -> {
                    mType = 2
                }
                2 -> {
                    mType = 3
                }
                3 -> {
                    mType = 4
                }
                4 -> {
                    mType = 0
                }
            }
            resolveTypeUI()
        }

        //旋转播放角度
        mChangeRotate.setOnClickListener {
            //没有播放过
            if (!mHadPlay) {
                return@setOnClickListener
            }
            if (mTextureView.rotation - mRotate == 270f) {
                mTextureView.rotation = mRotate.toFloat()
                mTextureView.requestLayout()
            } else {
                mTextureView.rotation = mTextureView.rotation + 90
                mTextureView.requestLayout()
            }
        }

        //镜像旋转
        mChangeTransform.setOnClickListener {
            if (!mHadPlay) {
                return@setOnClickListener
            }
            when (mTransformSize) {
                0 -> {
                    mTransformSize = 1
                }
                1 -> {
                    mTransformSize = 2
                }
                2 -> {
                    mTransformSize = 0
                }
            }
            resolveTransform()
        }
    }

    /**
     * Surface 状态变化回调
     */
    override fun onSurfaceSizeChanged(surface: Surface?, width: Int, height: Int) {
        super.onSurfaceSizeChanged(surface, width, height)
        resolveTransform()
    }

    /**
     * 处理镜像旋转
     * 注意，暂停时
     */
    protected fun resolveTransform() {
        when (mTransformSize) {
            1 -> {
                val transform = Matrix()
                transform.setScale(-1f, 1f, mTextureView.width / 2.toFloat(), 0f)
                mTextureView.setTransform(transform)
                mChangeTransform.text = "左右镜像"
                mTextureView.invalidate()
            }
            2 -> {
                val transform = Matrix()
                transform.setScale(1f, -1f, 0f, mTextureView.height / 2.toFloat())
                mTextureView.setTransform(transform)
                mChangeTransform.text = "上下镜像"
                mTextureView.invalidate()
            }
            0 -> {
                val transform = Matrix()
                transform.setScale(1f, 1f, mTextureView.width / 2.toFloat(), 0f)
                mTextureView.setTransform(transform)
                mChangeTransform.text = "旋转镜像"
                mTextureView.invalidate()
            }
        }
    }

    /**
     * 全屏时将对应处理参数逻辑赋给全屏播放器
     *
     * @param context
     * @param actionBar
     * @param statusBar
     * @return
     */
    override fun startWindowFullscreen(
        context: Context?,
        actionBar: Boolean,
        statusBar: Boolean
    ): GSYBaseVideoPlayer? {
        val sampleVideo:ControlVideoPlayer =
            super.startWindowFullscreen(
                context,
                actionBar,
                statusBar
            ) as ControlVideoPlayer
        sampleVideo.mSourcePosition = mSourcePosition
        sampleVideo.mType = mType
        sampleVideo.mTransformSize = mTransformSize
        //sampleVideo.resolveTransform();
        sampleVideo.resolveTypeUI()
        //sampleVideo.resolveRotateUI();
        //这个播放器的demo配置切换到全屏播放器
        //这只是单纯的作为全屏播放显示，如果需要做大小屏幕切换，请记得在这里耶设置上视频全屏的需要的自定义配置
        //比如已旋转角度之类的等等
        //可参考super中的实现
        return sampleVideo
    }
    /**
     * 推出全屏时将对应处理参数逻辑返回给非播放器
     *
     * @param oldF
     * @param vp
     * @param gsyVideoPlayer
     */
    override fun resolveNormalVideoShow(
        oldF: View?,
        vp: ViewGroup?,
        gsyVideoPlayer: GSYVideoPlayer?
    ) {
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer)
        if (gsyVideoPlayer != null) {
            val sampleVideo: ControlVideoPlayer =
                gsyVideoPlayer as ControlVideoPlayer
            mSourcePosition = sampleVideo.mSourcePosition
            mType = sampleVideo.mType
            mTransformSize = sampleVideo.mTransformSize
            resolveTypeUI()
        }
    }
    /**
     * 旋转逻辑
     */
    private fun resolveRotateUI() {
        if (!mHadPlay) {
            return
        }
        mTextureView.rotation = mRotate.toFloat()
        mTextureView.requestLayout()
    }

    /**
     * 显示比例
     * 注意，GSYVideoType.setShowType是全局静态生效，除非重启APP。
     */
    private fun resolveTypeUI() {
        if (!mHadPlay) {
            return
        }
        if (mType == 1) {
            mMoreScale.text = "16:9"
            GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_16_9)
        } else if (mType == 2) {
            mMoreScale.text = "4:3"
            GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_4_3)
        } else if (mType == 3) {
            mMoreScale.text = "全屏"
            GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL)
        } else if (mType == 4) {
            mMoreScale.text = "拉伸全屏"
            GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL)
        } else if (mType == 0) {
            mMoreScale.text = "默认比例"
            GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_DEFAULT)
        }
        changeTextureViewShowType()
        if (mTextureView != null) mTextureView.requestLayout()
    }
}