package com.lancer.eyelast.demo.bar

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gyf.immersionbar.ImmersionBar
import com.lancer.eyelast.R
import kotlinx.android.synthetic.main.activity_bar.*

class BarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar)
        init()
    }

    private fun init() {
        hasStatusBar()
    }


    //没有状态栏的全屏模式
    private fun fullScreen() {
        toolBar.title = "bar测试"
        toolBar.setTitleTextColor(Color.BLACK)
        toolBar.setBackgroundColor(Color.WHITE)
        ImmersionBar.hideStatusBar(window)
    }

    /**
     * 带透明状态栏的全屏模式,透明状态栏属性是transparentBar
     *
     * 当状态栏和layout都为透明时，状态栏字体看不清，这时候就要加上属性statusBarDarkFont
     *
     * 这时候如果有toolbar可能会有重叠问题，记得加上fitsSystemWindows属性防止重叠
     * */
    private fun transFullScreen() {
        ImmersionBar.with(this)
            .transparentBar()
            .fitsSystemWindows(true)
            .statusBarDarkFont(true)
            .init()
        toolBar.title = "bar测试"
        toolBar.setTitleTextColor(Color.BLACK)
        toolBar.setBackgroundColor(Color.TRANSPARENT)
    }


    private fun hasStatusBar() {
        ImmersionBar.with(this)
            .fitsSystemWindows(true)
            .statusBarColor(R.color.colorAccent)
            .init()
        toolBar.title = "bar测试"
        toolBar.setTitleTextColor(Color.BLACK)
        toolBar.setBackgroundResource(R.color.colorAccent)
    }
}