package com.lancer.eyelast.ui

import android.content.Intent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseActivity
import com.lancer.eyelast.databinding.ActivityMainBinding
import com.lancer.eyelast.demo.bar.BarActivity
import com.lancer.eyelast.demo.pic.PicSelectActivity
import com.lancer.eyelast.demo.player.PlayerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun initView() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home,
//                R.id.navigation_community,
//                R.id.navigation_notifications,
//                R.id.navigation_mine
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        floatButton.setOnClickListener {
            startActivity(Intent(this, PicSelectActivity::class.java))
        }
        floatButton.setOnLongClickListener {
            startActivity(Intent(this, BarActivity::class.java))
            return@setOnLongClickListener true
        }



    }

    override fun initData() {
    }

    override fun initLayout(): Int = R.layout.activity_main


    class OnDoubleClickListener(
        /**
         * 自定义回调接口
         */
        private val mCallback: DoubleClickCallback?
    ) :
        OnTouchListener {
        private var count = 0 //点击次数
        private var firstClick: Long = 0 //第一次点击时间
        private var secondClick: Long = 0 //第二次点击时间

        /**
         * 两次点击时间间隔，单位毫秒
         */
        private val totalTime = 1000

        interface DoubleClickCallback {
            fun onDoubleClick()
        }

        /**
         * 触摸事件处理
         * @param v
         * @param event
         * @return
         */
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            if (MotionEvent.ACTION_DOWN == event.action) { //按下
                count++
                if (1 == count) {
                    firstClick = System.currentTimeMillis() //记录第一次点击时间
                } else if (2 == count) {
                    secondClick = System.currentTimeMillis() //记录第二次点击时间
                    if (secondClick - firstClick < totalTime) { //判断二次点击时间间隔是否在设定的间隔时间之内
                        mCallback?.onDoubleClick()
                        count = 0
                        firstClick = 0
                    } else {
                        firstClick = secondClick
                        count = 1
                    }
                    secondClick = 0
                }
            }
            return true
        }

    }
}