package com.lancer.eyelast.ui.activity.video

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseActivity
import com.lancer.eyelast.bean.Author
import com.lancer.eyelast.bean.Consumption
import com.lancer.eyelast.bean.Cover
import com.lancer.eyelast.bean.WebUrl
import com.lancer.eyelast.databinding.ActivityVideoBinding
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.parcel.Parcelize

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