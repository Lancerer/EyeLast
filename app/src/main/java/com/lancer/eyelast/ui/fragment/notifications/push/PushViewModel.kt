package com.lancer.eyelast.ui.fragment.notifications.push

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lancer.eyelast.api.MainPageApiService
import com.lancer.eyelast.bean.PushMessage
import com.lancer.eyelast.model.MainPageRepository
import com.lancer.eyelast.network.scheduler.BaseSubscriber
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener

class PushViewModel(private val repository: MainPageRepository) : ViewModel() {

    var nextPageUrl: String? = null
    private var requestParamLiveData = MutableLiveData<String>()

    /**
     * 可以使用liveData加上携程替换
     */
    fun requestPush(
        listener: OnNextWithErrorListener<PushMessage>,
        url: String = MainPageApiService.PUSHMESSAGE_URL
    ) {
        repository.requestPushMessage(url)?.subscribe(
            BaseSubscriber(object : OnNextWithErrorListener<PushMessage> {
                override fun onNext(t: PushMessage?) {
                    listener.onNext(t)
                }
                override fun onError(e: Throwable?) {
                    listener.onError(e)
                }
            })
        )
    }

    fun onRefresh() {
        requestParamLiveData.value = MainPageApiService.PUSHMESSAGE_URL
    }

    fun onLoadMore() {
        requestParamLiveData.value = nextPageUrl ?: ""
    }
}