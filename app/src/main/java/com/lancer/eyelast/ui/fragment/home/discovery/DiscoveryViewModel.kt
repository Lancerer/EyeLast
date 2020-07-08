package com.lancer.eyelast.ui.fragment.home.discovery

import androidx.lifecycle.ViewModel
import com.lancer.eyelast.api.MainPageApiService
import com.lancer.eyelast.bean.Discovery
import com.lancer.eyelast.model.MainPageRepository
import com.lancer.eyelast.network.scheduler.BaseSubscriber
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener

class DiscoveryViewModel(private val repository: MainPageRepository) : ViewModel() {

    fun requestDiscovery(
        listener: OnNextWithErrorListener<Discovery>,
        url: String = MainPageApiService.DISCOVERY_URL
    ) {
        repository.requestDiscovery(url)
            .subscribe(BaseSubscriber<Discovery>(object : OnNextWithErrorListener<Discovery> {
                override fun onNext(t: Discovery?) {
                    listener.onNext(t)
                }

                override fun onError(e: Throwable?) {
                    listener.onError(e)
                }

            }))
    }
}