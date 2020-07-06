package com.lancer.eyelast.ui.fragment.home.commend

import androidx.lifecycle.ViewModel
import com.lancer.eyelast.api.MainPageApiService
import com.lancer.eyelast.bean.Daily
import com.lancer.eyelast.bean.HomePageRecommend
import com.lancer.eyelast.model.MainPageRepository
import com.lancer.eyelast.network.scheduler.BaseSubscriber
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener

class CommendViewModel(private val repository: MainPageRepository) : ViewModel() {
    var dataList = ArrayList<HomePageRecommend.Item>()

    fun requestRecommend(listener: OnNextWithErrorListener<HomePageRecommend>, url: String=MainPageApiService.HOMEPAGE_RECOMMEND_URL) {
        repository.requestRecommend(url)?.subscribe(
            BaseSubscriber(object : OnNextWithErrorListener<HomePageRecommend> {
                override fun onNext(t: HomePageRecommend?) {
                    listener.onNext(t)
                }
                override fun onError(e: Throwable?) {
                    listener.onError(e)
                }
            })
        )

    }
}