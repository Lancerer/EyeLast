package com.lancer.eyelast.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {
    //    protected var mStatusView: MultipleStatusView? = null
    protected lateinit var binding: V
    var name = "base"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(name, "onCreate")
        binding = DataBindingUtil.setContentView(this, initLayout())
        initView()
        initData()
    }

    override fun onStart() {
        super.onStart()
        Log.d(name, "onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.d(name, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(name, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(name, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(name, "onDestroy")

    }
    abstract fun initView()
    abstract fun initData()
    abstract fun initLayout(): Int
}