package com.lancer.eyelast.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

 abstract class BaseFragment<V : ViewDataBinding> : Fragment() {
    protected lateinit var binding: V
    var name = "base"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(name, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(name, "onCreateView")
        binding = DataBindingUtil.inflate(inflater, initLayout(), container, false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(name, "onActivityCreated")

        initView()
        initData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(name, "onViewCreated")

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

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(name, "onDestroyView")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(name, "onDestroy")
    }


    abstract fun initView()

    abstract fun initData()

    abstract fun initLayout(): Int
}