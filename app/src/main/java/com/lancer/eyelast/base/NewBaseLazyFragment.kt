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
 * @des new 新版懒加载使用setMaxLifecycle方法，他可以设置Fragment最大生命周期
 * 当设置为START,他执行的最大生命周期就是onStart，超过的会强制回退到onStart，不到的也会提到onStart
 *
 * 新版中处理在FragmentStatePagerAdapter构造器中即可处理：BEHAVIOR_SET_USER_VISIBLE_HINT是兼容老代码的意思
 * BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT该参数表示只有当前显示的Fragment的生命周期才会被执行到onResume，其他的都只能执行到onStart
 *
 * 也就是在切换的同时CurrentFragment的最大生命周期会被切换到onStart，也就会执行onPause，下一个替代他成为CurrentFragment的fragment最大生命周期就会被
 * 切换为onResume。此前该fragment最大生命周期一直都是onStart ,具体看NotificationFragment
 * @Date 2020/7/7 13:56
 */
abstract class NewBaseLazyFragment<V : ViewDataBinding> : Fragment() {
    protected lateinit var binding: V

    private lateinit var mActivity: Activity

    var name = "base"

    private var isFirstLoad = true


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

    override fun onResume() {
        super.onResume()
        Log.d(name, "onResume")
        if (isFirstLoad) {
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