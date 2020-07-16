package com.lancer.eyelast.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseActivity
import com.lancer.eyelast.databinding.ActivityMainBinding
import com.lancer.eyelast.demo.bar.BarActivity
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
            startActivity(Intent(this, PlayerActivity::class.java))
        }
        floatButton.setOnLongClickListener {
            startActivity(Intent(this, BarActivity::class.java))
            return@setOnLongClickListener true
        }
    }

    override fun initData() {
    }

    override fun initLayout(): Int = R.layout.activity_main

}