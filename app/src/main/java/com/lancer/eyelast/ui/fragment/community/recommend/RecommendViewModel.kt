package com.lancer.eyelast.ui.fragment.community.recommend

import androidx.lifecycle.ViewModel
import com.lancer.eyelast.api.MainPageApiService
import com.lancer.eyelast.bean.CommunityRecommend
import com.lancer.eyelast.model.MainPageRepository
import com.lancer.eyelast.network.scheduler.BaseSubscriber
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener

class RecommendViewModel(private val repository: MainPageRepository) : ViewModel() {


    fun requestRecommend(listener: OnNextWithErrorListener<CommunityRecommend>, url: String=MainPageApiService.COMMUNITY_RECOMMEND_URL) {
        repository.requestCommunityRecommend(url)
            ?.subscribe(BaseSubscriber(object : OnNextWithErrorListener<CommunityRecommend> {
                override fun onNext(t: CommunityRecommend?) {
                    listener.onNext(t)
                }
                override fun onError(e: Throwable?) {
                    listener.onError(e)
                }

            }))
    }
}