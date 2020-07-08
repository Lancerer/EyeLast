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
import com.lancer.eyelast.databinding.LayoutCommonMultipleRefreshRecyclerBinding
import com.lancer.eyelast.network.exception.ExceptionHandle
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener
import com.lancer.eyelast.utils.InjectorUtil
import com.scwang.smart.refresh.layout.constant.RefreshState

/**
 * 日报
 */
class DailyFragment : BaseFragment<LayoutCommonMultipleRefreshRecyclerBinding>() {


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
        binding.multipleStatusView.showLoading()
    }

    override fun initView() {
        adapter = DailyAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter

        binding.refreshLayout.setOnRefreshListener {
            viewModel.requestDaily(listener)
        }
        binding.refreshLayout.setOnLoadMoreListener {
            viewModel.requestDaily(listener, nextPageUrl!!)
        }

    }

    override fun initData() {
        viewModel.requestDaily(listener)
    }


    inner class MyListener : OnNextWithErrorListener<Daily> {
        override fun onNext(response: Daily?) {
            nextPageUrl = response?.nextPageUrl
            binding.multipleStatusView.showContent()
            if (response?.itemList == null) {
                binding.multipleStatusView.showEmpty()
                return
            }
            when (binding.refreshLayout.state) {
                RefreshState.None, RefreshState.Refreshing -> {
                    adapter.data.clear()
                    adapter.addData(response.itemList)
                }
                RefreshState.Loading -> {
                    adapter.addData(response.itemList)
                }
                else -> {
                }
            }
            if (response.nextPageUrl.isNullOrEmpty()) {
                binding.refreshLayout.finishLoadMoreWithNoMoreData()
            } else {
                binding.refreshLayout.closeHeaderOrFooter()
            }
        }

        override fun onError(e: Throwable?) {
            Log.d("TAG", ExceptionHandle.handleException(e!!))
        }
    }

    override fun initLayout(): Int = R.layout.layout_common_multiple_refresh_recycler

}