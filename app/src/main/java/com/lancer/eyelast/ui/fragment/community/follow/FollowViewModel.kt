package com.lancer.eyelast.ui.fragment.community.follow

import androidx.lifecycle.ViewModel
import com.lancer.eyelast.api.MainPageApiService
import com.lancer.eyelast.bean.Follow
import com.lancer.eyelast.model.MainPageRepository
import com.lancer.eyelast.network.scheduler.BaseSubscriber
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener

class FollowViewModel(private val repository: MainPageRepository) : ViewModel() {

    fun requestFollowList(
        listener: OnNextWithErrorListener<Follow>,
        url: String = MainPageApiService.FOLLOW_URL
    ) {
        repository.requestFollowList(url)
            ?.subscribe(BaseSubscriber(object : OnNextWithErrorListener<Follow> {
                override fun onNext(t: Follow?) {
                    listener.onNext(t)
                }

                override fun onError(e: Throwable?) {
                    listener.onError(e)
                }
            }))

    }
}