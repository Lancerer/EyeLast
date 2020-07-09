package com.lancer.eyelast.ui.fragment.home.commend

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.bean.Daily
import com.lancer.eyelast.bean.HomePageRecommend
import com.lancer.eyelast.databinding.LayoutCommonMultipleRefreshRecyclerBinding
import com.lancer.eyelast.network.exception.ExceptionHandle
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener
import com.lancer.eyelast.utils.InjectorUtil
import com.scwang.smart.refresh.layout.constant.RefreshState

/**
 * 推荐界面
 */
class CommendFragment : BaseFragment<LayoutCommonMultipleRefreshRecyclerBinding>() {

    init {
        name = CommendFragment::class.java.simpleName
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectorUtil.getHomePageCommendViewModelFactory()
        ).get(CommendViewModel::class.java)
    }

    private lateinit var adapter: CommendAdapter

    private val listener = MyListener()

    var dataList = ArrayList<HomePageRecommend.Item>()

    var nextPageUrl: String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        multipleStatusView = binding.multipleStatusView
        binding.multipleStatusView.showLoading()
    }

    override fun initView() {
        adapter = CommendAdapter(this, dataList)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
        binding.refreshLayout.setOnRefreshListener {
            //下拉刷新
            viewModel.requestRecommend(listener)
        }
        binding.refreshLayout.setOnLoadMoreListener {
            //上拉加载更多
            if (nextPageUrl != null) {
                viewModel.requestRecommend(listener, nextPageUrl!!)
            } else {
                binding.refreshLayout.finishLoadMoreWithNoMoreData()
                binding.multipleStatusView.showError()
            }
        }
    }

    override fun initData() {
        viewModel.requestRecommend(listener)
    }


    inner class MyListener : OnNextWithErrorListener<HomePageRecommend> {
        override fun onNext(response: HomePageRecommend?) {
            multipleStatusView?.showContent()
            if (response?.itemList == null) {
                binding.multipleStatusView.showEmpty()
                return
            }
            nextPageUrl = response.nextPageUrl
            if (binding.refreshLayout.state == RefreshState.None || binding.refreshLayout.state == RefreshState.Refreshing) {
                dataList.clear()
                dataList.addAll(response.itemList)
            } else if (binding.refreshLayout.state == RefreshState.Loading) {
                dataList.addAll(response.itemList)
                adapter.notifyDataSetChanged()
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


    override fun initLayout(): Int {
        return R.layout.layout_common_multiple_refresh_recycler
    }

}