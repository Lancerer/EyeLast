package com.lancer.eyelast.ui.fragment.home.daily

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.bean.Daily
import com.lancer.eyelast.databinding.FragmentDailyBinding
import com.lancer.eyelast.network.exception.ExceptionHandle
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener
import com.lancer.eyelast.utils.InjectorUtil

/**
 * 日报
 */
class DailyFragment : BaseFragment<FragmentDailyBinding>() {


    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectorUtil.getDailyViewModelFactory()
        ).get(DailyViewModel::class.java)
    }

    private lateinit var adapter: DailyAdapter

    private var nextPageUrl: String? = null

    private val listener = MyListener()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dailyMultiple.showLoading()
    }

    override fun initView() {
        adapter = DailyAdapter(this)
        binding.dailyRecycler.layoutManager = LinearLayoutManager(activity)
        binding.dailyRecycler.adapter = adapter

        //加载更多逻辑
        val loadMoreModule = adapter.loadMoreModule
        loadMoreModule.isEnableLoadMore = true
        loadMoreModule.isAutoLoadMore = true
        loadMoreModule.isEnableLoadMoreIfNotFullPage = true
        loadMoreModule.enableLoadMoreEndClick = true
        loadMoreModule.setOnLoadMoreListener {
            if (nextPageUrl == null) {
                adapter.loadMoreModule.loadMoreFail()
                Log.d("nextUrl","loadMoreFail")

            } else {
                Log.d("nextUrl","nextUrl==${nextPageUrl}")
                viewModel.requestDaily(listener, nextPageUrl!!)
                //加载完成要调用该方法，表示本次加载更多完毕
                loadMoreModule.loadMoreComplete()
            }
        }

    }

    override fun initData() {
        viewModel.requestDaily(listener)
    }

    override fun initLayout(): Int = R.layout.fragment_daily

    private val dailyList = mutableListOf<Daily.Item>()

    inner class MyListener : OnNextWithErrorListener<Daily> {
        override fun onNext(response: Daily?) {
            binding.dailyMultiple.showContent()
            if (response?.itemList == null) {
                binding.dailyMultiple.showEmpty()
                return
            }

            dailyList.addAll(response.itemList)
            adapter.addData(dailyList)

            nextPageUrl = response.nextPageUrl
        }

        override fun onError(e: Throwable?) {
            Log.d("TAG", ExceptionHandle.handleException(e!!))
        }
    }
}