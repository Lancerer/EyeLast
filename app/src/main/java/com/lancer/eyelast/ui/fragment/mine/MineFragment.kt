package com.lancer.eyelast.ui.fragment.mine

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.lancer.eyelast.BaseApplication
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.databinding.FragmentMineBinding
import com.lancer.eyelast.utils.AppUtils
import com.lancer.eyelast.utils.CommonUtils
import com.lancer.eyelast.utils.CommonUtils.getImageContentUri
import com.lancer.eyelast.utils.CommonUtils.handleImage
import com.lancer.eyelast.utils.FileUtils
import com.lancer.eyelast.utils.FileUtils.createRootPath
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_mine.*
import java.io.File

class MineFragment : BaseFragment<FragmentMineBinding>(), View.OnClickListener {
    init {
        name = MineFragment::class.java.name
    }

    companion object {
        fun newInstance() = MineFragment()
        private const val PHOTO_CODE = 0X1111
        private const val CAMERA_CODE = 0X2222
        private const val CORP_CODE = 0X3333
    }


    private lateinit var mTempFile: File
    private var mCropImageFile: File? = null


    override fun initView() {
        binding.mineVersionTv.text = AppUtils.getVerName(BaseApplication.context)
        binding.mineMoreIv.setOnClickListener(this)
        binding.mineAvatarIv.setOnClickListener(this)
        binding.mineTipsTv.setOnClickListener(this)
        binding.mineFavoritesTv.setOnClickListener(this)
        binding.mineCacheTv.setOnClickListener(this)
        binding.mineFollowTv.setOnClickListener(this)
        binding.mineRecordTv.setOnClickListener(this)
        binding.mineNotificationToggleTv.setOnClickListener(this)
        binding.mineBadgeTv.setOnClickListener(this)
        binding.mineContributeTv.setOnClickListener(this)
        binding.mineFeedbackTv.setOnClickListener(this)
        binding.mineVersionTv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                R.id.mine_more_iv -> {
                    //查看更多
                }
                R.id.mine_version_tv -> {
                    //版本
                }
                R.id.mine_feedback_tv -> {
                    //反馈
                }
                R.id.mine_contribute_tv -> {
                    //投稿
                }
                R.id.mine_tips_tv, R.id.mine_favorites_tv, R.id.mine_cache_tv, R.id.mine_follow_tv, R.id.mine_record_tv, R.id.mine_notification_toggle_tv, R.id.mine_badge_tv -> {

                }
                R.id.mine_avatar_iv -> {
                    //更换头像
                    avatarSetting()
                }
            }
        }

    }

    /**
     * 头像设置
     */
    private fun avatarSetting() {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        val view = View.inflate(context, R.layout.dialog_avatar_select, null)
        val camera = view.findViewById<TextView>(R.id.dialog_camera)
        val photo = view.findViewById<TextView>(R.id.dialog_photo)

        //照相机
        camera.setOnClickListener {
            activity?.let {
                if (CommonUtils.isGranted(Manifest.permission.CAMERA)) {
                    openCamera()
                } else {
                    RxPermissions(it).request(
                        Manifest.permission.CAMERA
                    )
                        .subscribe { isGrand ->
                            if (isGrand) {
                                Toast.makeText(context, "请求权限成功", Toast.LENGTH_SHORT).show()
                                openCamera()
                            } else {
                                Toast.makeText(context, "请求权限失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
            dialog.dismiss()
        }

        //相册
        photo.setOnClickListener {
            openPhoto()
            dialog.dismiss()
        }

        dialog.setView(view)
        dialog.show()
    }


    /**
     * result返回结果处理
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            //相册选择返回
            PHOTO_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val imagePath = handleImage(data, activity)
                    cropPhoto(imagePath)
                } else {
                    Toast.makeText(context, "打开图库失败", Toast.LENGTH_SHORT).show()
                }
            }
            //照相机选择返回
            CAMERA_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    cropPhoto(mTempFile.absolutePath)
                } else {
                    Toast.makeText(context, "拍照失败", Toast.LENGTH_SHORT).show()

                }
            }
            //剪切图片返回
            CORP_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    binding.mineAvatarIv.setImageURI(Uri.fromFile(mCropImageFile))
                } else {
                    Toast.makeText(context, "剪切失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * 剪切图片
     */
    private fun cropPhoto(imagePath: String) {
        mCropImageFile = CommonUtils.geTempCropImageFile(activity)
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(getImageContentUri(File(imagePath), activity), "image/*")
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("outputX", 500)
        intent.putExtra("outputY", 500)
        intent.putExtra("scale", true)
        intent.putExtra("return-data", false)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCropImageFile))
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true)
        startActivityForResult(intent, CORP_CODE)
    }

    /**
     * 打开摄像头
     */
    @SuppressLint("UseRequireInsteadOfGet")
    private fun openCamera() {
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE, null)
        if (captureIntent.resolveActivity(activity!!.packageManager) != null) {
            mTempFile =
                File(createRootPath(context) + "/" + System.currentTimeMillis() + ".jpg")
            FileUtils.createFile(mTempFile)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                captureIntent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    FileProvider.getUriForFile(
                        context!!,
                        context!!.applicationContext.packageName + ".provider",
                        mTempFile
                    )
                )
            } else {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTempFile))
            }

        }
        startActivityForResult(captureIntent, CAMERA_CODE)
    }

    /**
     * 打开相册
     */
    private fun openPhoto() {
        val intent = Intent()
        intent.type = "image/*";
        intent.action = Intent.ACTION_GET_CONTENT;
        startActivityForResult(intent, PHOTO_CODE)
    }

    override fun initData() {
    }

    override fun initLayout(): Int = R.layout.fragment_mine


}