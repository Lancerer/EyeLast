package com.lancer.eyelast.ui.fragment.community.recommend


import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.bean.CommunityRecommend
import com.lancer.eyelast.databinding.LayoutCommonMultipleRefreshRecyclerBinding
import com.lancer.eyelast.extension.dp2px
import com.lancer.eyelast.network.exception.ExceptionHandle
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener
import com.lancer.eyelast.utils.GlobalUtil
import com.lancer.eyelast.utils.InjectorUtil
import com.scwang.smart.refresh.layout.constant.RefreshState


//TODO 宽度设计
class RecommendFragment : BaseFragment<LayoutCommonMultipleRefreshRecyclerBinding>(),
    OnNextWithErrorListener<CommunityRecommend> {
    init {
        name = RecommendFragment::class.java.simpleName
    }

    /**
     * 列表左or右间距
     */
    val bothSideSpace = GlobalUtil.getDimension(R.dimen.listSpaceSize)

    /**
     * 列表中间内间距，左or右。
     */
    val middleSpace = dp2px(3f)

    /**
     * 通过获取屏幕宽度来计算出每张图片最大的宽度。
     *
     * @return 计算后得出的每张图片最大的宽度。
     */
    val maxImageWidth: Int
        get() {
            val windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val metrics = DisplayMetrics()
            windowManager.defaultDisplay?.getMetrics(metrics)
            val columnWidth = metrics.widthPixels
            return (columnWidth - ((bothSideSpace * 2) + (middleSpace * 2))) / 2
        }

    private lateinit var mAdapter: RecommendAdapter


    private var nextPageUrl: String? = null

    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getCommunityRecommendViewModelFactory())
            .get(RecommendViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        multipleStatusView = binding.multipleStatusView
        multipleStatusView?.showLoading()
    }

    override fun initView() {
        mAdapter = RecommendAdapter(this)
        binding.recyclerView.adapter = mAdapter
        val mainLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mainLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.recyclerView.layoutManager = mainLayoutManager

        binding.refreshLayout.setOnLoadMoreListener {
            viewModel.requestRecommend(this, nextPageUrl!!)
        }
        binding.refreshLayout.setOnRefreshListener {
            viewModel.requestRecommend(this)
        }
    }

    override fun initData() {
        viewModel.requestRecommend(this)
    }


    override fun onNext(response: CommunityRecommend?) {
        multipleStatusView?.showContent()
        response?.let {
            nextPageUrl = it.nextPageUrl

            if (it.itemList.isEmpty()) {
                multipleStatusView?.showEmpty()
                return
            }
        }

        when (binding.refreshLayout.state) {
            RefreshState.None, RefreshState.Refreshing -> {
                mAdapter.data.clear()
                mAdapter.addData(response!!.itemList)
            }
            RefreshState.Loading -> {
                mAdapter.addData(response!!.itemList)
            }
            else -> {
            }
        }
        if (response?.nextPageUrl.isNullOrEmpty()) {
            binding.refreshLayout.finishLoadMoreWithNoMoreData()
        } else {
            binding.refreshLayout.closeHeaderOrFooter()
        }
    }

    override fun onError(e: Throwable?) {
        multipleStatusView?.showError()
        Log.d(javaClass.simpleName, ExceptionHandle.handleException(e!!))
    }


    override fun initLayout(): Int = R.layout.layout_common_multiple_refresh_recycler


}