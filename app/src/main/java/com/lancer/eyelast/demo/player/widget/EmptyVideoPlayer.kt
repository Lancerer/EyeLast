package com.lancer.eyelast.demo.player.widget

import android.content.Context
import android.util.AttributeSet
import com.lancer.eyelast.R
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

/**
 * @author lancer
 * @des 空实现的播放器，就一个FrameLayout，禁止一切手势等控制逻辑
 * @Date 2020/7/15 9:56
 */
class EmptyVideoPlayer : StandardGSYVideoPlayer {
    constructor(context: Context) : super(context)
    constructor(context: Context, fullFlag: Boolean) : super(context, fullFlag)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun getLayoutId(): Int = R.layout.player_empty_control_video


    override fun touchSurfaceMoveFullLogic(absDeltaX: Float, absDeltaY: Float) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY)
        //不给触摸快进，如果需要，屏蔽下方代码即可
        mChangePosition = false

        //不给触摸音量，如果需要，屏蔽下方代码即可
        mChangeVolume = false

        //不给触摸亮度，如果需要，屏蔽下方代码即可
        mBrightness = false
    }

    override fun touchDoubleUp() {
        //super.touchDoubleUp()
        //不需要双击暂停
    }
}