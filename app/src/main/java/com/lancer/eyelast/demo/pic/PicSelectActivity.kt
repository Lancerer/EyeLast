package com.lancer.eyelast.demo.pic

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.lancer.eyelast.R
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import kotlinx.android.synthetic.main.activity_pic_select.*

class PicSelectActivity : AppCompatActivity() {

    companion object {
        private val TAG = PicSelectActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pic_select)
        initData()
    }

    private fun initData() {

        photo.setOnClickListener {
            //相册获取

            PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .forResult(object:OnResultCallbackListener<LocalMedia>{
                    override fun onResult(result: MutableList<LocalMedia>?) {
                        Glide.with(this@PicSelectActivity).load(result?.get(0)!!.path).into(iv)
                    }

                    override fun onCancel() {
                    }

                })
        }

        camera.setOnClickListener {
            //点击拍照获取
            PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: MutableList<LocalMedia>?) {
                        for (media in result!!) {
                            Log.i(TAG, "是否压缩:" + media.isCompressed)
                            Log.i(TAG, "压缩:" + media.compressPath)
                            Log.i(TAG, "原图:" + media.path)
                            Log.i(TAG, "是否裁剪:" + media.isCut)
                            Log.i(TAG, "裁剪:" + media.cutPath)
                            Log.i(TAG, "是否开启原图:" + media.isOriginal)
                            Log.i(TAG, "原图路径:" + media.originalPath)
                            Log.i(
                                TAG,
                                "Android Q 特有Path:" + media.androidQToPath
                            )
                            Log.i(
                                TAG,
                                "宽高: " + media.width + "x" + media.height
                            )
                            Log.i(TAG, "Size: " + media.size)
                        }
                        Glide.with(this@PicSelectActivity).load(result[0].path).into(iv)
                    }

                    override fun onCancel() {
                        Log.i(TAG, "取消")
                    }

                })
        }
    }
}