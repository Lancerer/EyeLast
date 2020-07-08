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
import com.lancer.eyelast.databinding.FragmentRecommendBinding
import com.lancer.eyelast.extension.dp2px
import com.lancer.eyelast.network.exception.ExceptionHandle
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener
import com.lancer.eyelast.utils.GlobalUtil
import com.lancer.eyelast.utils.InjectorUtil
import com.scwang.smart.refresh.layout.constant.RefreshState


class RecommendFragment : BaseFragment<FragmentRecommendBinding>(),
    OnNextWithErrorListener<CommunityRecommend> {
    init {
        name = javaClass.simpleName
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

    private var dataList = ArrayList<CommunityRecommend.Item>()

    private var nextPageUrl: String? = null

    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getCommunityRecommendViewModelFactory())
            .get(RecommendViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recommendMultiple.showLoading()
    }

    override fun initView() {
        mAdapter = RecommendAdapter(this, dataList)
        binding.recommendRecycler.adapter = mAdapter
        val mainLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mainLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.recommendRecycler.layoutManager =mainLayoutManager

        binding.recommendRefresh.setOnLoadMoreListener {
            viewModel.requestRecommend(this, nextPageUrl!!)
        }
        binding.recommendRefresh.setOnRefreshListener {
            viewModel.requestRecommend(this)
        }
    }

    override fun initData() {
        viewModel.requestRecommend(this)
    }

    override fun initLayout(): Int = R.layout.fragment_recommend

    override fun onNext(response: CommunityRecommend?) {
        binding.recommendMultiple.showContent()
        response?.let {
            nextPageUrl = it.nextPageUrl

            if (it.itemList.isEmpty()) {
                binding.recommendMultiple.showEmpty()
                return
            }
        }

        when (binding.recommendRefresh.state) {
            RefreshState.None, RefreshState.Refreshing -> {
                dataList.clear()
                dataList.addAll(response!!.itemList)
            }
            RefreshState.Loading -> {
                dataList.addAll(response!!.itemList)
                mAdapter.notifyDataSetChanged()
            }
            else -> {
            }
        }
        if (response?.nextPageUrl.isNullOrEmpty()) {
            binding.recommendRefresh.finishLoadMoreWithNoMoreData()
        } else {
            binding.recommendRefresh.closeHeaderOrFooter()
        }
    }

    override fun onError(e: Throwable?) {
        binding.recommendMultiple.showError()
        Log.d(javaClass.simpleName, ExceptionHandle.handleException(e!!))
    }


}