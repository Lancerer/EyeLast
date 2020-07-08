package com.lancer.eyelast.ui.fragment.home.discovery

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.bean.Discovery
import com.lancer.eyelast.databinding.LayoutCommonMultipleRefreshRecyclerBinding
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener
import com.lancer.eyelast.utils.InjectorUtil
import com.scwang.smart.refresh.layout.constant.RefreshState

/**
 * 发现
 */
class DiscoveryFragment : BaseFragment<LayoutCommonMultipleRefreshRecyclerBinding>(),
    OnNextWithErrorListener<Discovery> {

    init {
        name = DiscoveryFragment::class.java.simpleName
    }

    private var nextPageUrl: String? = null

    private lateinit var mAdapter: DiscoveryAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getDiscoveryViewModelFactory())
            .get(DiscoveryViewModel::class.java)
    }

    override fun initView() {
        mAdapter = DiscoveryAdapter(this)

        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.setHasFixedSize(true)

        binding.refreshLayout.setOnRefreshListener {
            viewModel.requestDiscovery(this)

        }
        binding.refreshLayout.setOnLoadMoreListener {
            viewModel.requestDiscovery(this, nextPageUrl!!)

        }
    }

    override fun initData() {
        viewModel.requestDiscovery(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        multipleStatusView = binding.multipleStatusView
        multipleStatusView?.showEmpty()
    }

    override fun onNext(response: Discovery?) {
        nextPageUrl = response?.nextPageUrl
        multipleStatusView?.showContent()
        if (response?.itemList == null) {
            multipleStatusView?.showEmpty()
            return
        }
        when (binding.refreshLayout.state) {
            RefreshState.None, RefreshState.Refreshing -> {
                mAdapter.data.clear()
                mAdapter.addData(response.itemList)
            }
            RefreshState.Loading -> {
                mAdapter.addData(response.itemList)
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
        multipleStatusView?.showError()
    }


    override fun initLayout(): Int = R.layout.layout_common_multiple_refresh_recycler


}