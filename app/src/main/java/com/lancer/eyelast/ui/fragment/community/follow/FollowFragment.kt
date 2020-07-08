package com.lancer.eyelast.ui.fragment.community.follow

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.bean.Follow
import com.lancer.eyelast.databinding.FragmentFollowBinding
import com.lancer.eyelast.network.exception.ExceptionHandle
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener
import com.lancer.eyelast.utils.InjectorUtil
import com.scwang.smart.refresh.layout.constant.RefreshState

class FollowFragment : BaseFragment<FragmentFollowBinding>(), OnNextWithErrorListener<Follow> {


    private lateinit var mAdapter: FollowAdapter

    private val dataList = ArrayList<Follow.Item>()

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectorUtil.getFollowListViewModelFactory()
        ).get(FollowViewModel::class.java)
    }

    private var nextPageUrl: String? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.followMultiple.showLoading()
    }

    override fun initView() {
        mAdapter = FollowAdapter(this, dataList)
        binding.followRecycler.layoutManager = LinearLayoutManager(activity)
        binding.followRecycler.adapter = mAdapter
        binding.followRefresh.setOnLoadMoreListener {
            viewModel.requestFollowList(this, nextPageUrl!!)
        }
        binding.followRefresh.setOnRefreshListener {
            viewModel.requestFollowList(this)
        }
    }

    override fun initData() {
        viewModel.requestFollowList(this)
    }

    override fun onNext(follow: Follow?) {
        binding.followMultiple.showContent()

        follow?.let {
            nextPageUrl = it.nextPageUrl

            if (it.itemList.isEmpty()) {
                binding.followMultiple.showEmpty()
                return
            }
        }

        when (binding.followRefresh.state) {
            RefreshState.None, RefreshState.Refreshing -> {
                dataList.clear()
                dataList.addAll(follow!!.itemList)
            }
            RefreshState.Loading -> {
                dataList.addAll(follow!!.itemList)
                mAdapter.notifyDataSetChanged()
            }
            else -> {

            }
        }
        if (follow?.nextPageUrl.isNullOrEmpty()) {
            binding.followRefresh.finishLoadMoreWithNoMoreData()
        } else {
            binding.followRefresh.closeHeaderOrFooter()
        }

    }

    override fun onError(e: Throwable?) {
        binding.followMultiple.showError()
        Log.d(javaClass.simpleName, ExceptionHandle.handleException(e!!))
    }

    override fun initLayout(): Int = R.layout.fragment_follow


}