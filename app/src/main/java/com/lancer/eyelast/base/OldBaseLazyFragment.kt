package com.lancer.eyelast.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @author lancer
 * @des old
 * @Date 2020/7/7 13:56
 */
abstract class OldBaseLazyFragment<V : ViewDataBinding> : Fragment() {
    protected lateinit var binding: V

    private lateinit var mActivity: Activity

    var name = "base"


    /**
     * 当前Fragment状态是否可见
     */
    private var isVisibleToUser: Boolean = false

    /**
     * 当前view是否创建
     */
    private var isViewCreated: Boolean = false

    /**
     * 是否是第一次加载数据
     */
    private var isFirstLoad: Boolean = true

    /**
     * isVisibleToUser:表示当前fragment是否当前对用户可见，执行顺序位于onAttach之前
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        onLazyLoad()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
        Log.d(name, "onAttach")
    }

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        onLazyLoad()
    }

    private fun onLazyLoad() {
        if (isVisibleToUser && isViewCreated && isFirstLoad) {
            isFirstLoad = false
            lazyLoadData()
        }
    }


    /**
     * 加载数据方法
     */
    protected abstract fun lazyLoadData()

    /**
     * 加载布局
     */
    abstract fun initLayout(): Int

}