package com.lancer.eyelast.ui.fragment.notifications.push

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.bean.PushMessage
import com.lancer.eyelast.databinding.FragmentPushBinding
import com.lancer.eyelast.network.exception.ExceptionHandle
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener
import com.lancer.eyelast.ui.fragment.notifications.letter.LetterFragment
import com.lancer.eyelast.utils.InjectorUtil

class PushFragment : BaseFragment<FragmentPushBinding>() {
    init {
        name = PushFragment::class.java.simpleName
    }

    companion object {
        fun newInstance() = PushFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pushMultiple.showLoading()

    }
    private var nextPageUrl: String? = null
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectorUtil.getPushViewModelFactory()
        ).get(PushViewModel::class.java)
    }
    private lateinit var mAdapter: PushAdapter

    private val listener = MyListener()
    override fun initView() {
        mAdapter = PushAdapter()
        binding.pushRv.layoutManager = LinearLayoutManager(context)
        binding.pushRv.adapter = mAdapter

        //加载更多逻辑
        val loadMoreModule = mAdapter.loadMoreModule
        loadMoreModule.isEnableLoadMore = true
        loadMoreModule.isAutoLoadMore = true
        loadMoreModule.isEnableLoadMoreIfNotFullPage = true
        loadMoreModule.enableLoadMoreEndClick = true
        loadMoreModule.setOnLoadMoreListener {
            if (nextPageUrl == null) {
                mAdapter.loadMoreModule.loadMoreFail()
            } else {
                viewModel.requestPush(listener, nextPageUrl!!)
                //加载完成要调用该方法，表示本次加载更多完毕
                loadMoreModule.loadMoreComplete()
            }
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            //会传递一个ActionUrl
        }
    }


    private val pushMessage = mutableListOf<PushMessage.Message>()
    override fun initData() {
        observe()
    }

    private fun observe() {
        viewModel.requestPush(listener)
    }

    override fun initLayout(): Int = R.layout.fragment_push


   private inner class MyListener : OnNextWithErrorListener<PushMessage> {
        override fun onNext(response: PushMessage?) {
            binding.pushMultiple.showContent()
            if (response?.itemList == null) {
                binding.pushMultiple.showEmpty()
                return
            }

            response.itemList.forEach {
                pushMessage.add(it)
            }
            mAdapter.addData(pushMessage)

            nextPageUrl = response.nextPageUrl
        }

        override fun onError(e: Throwable?) {
            Log.d("TAG", ExceptionHandle.handleException(e!!))
        }
    }
}