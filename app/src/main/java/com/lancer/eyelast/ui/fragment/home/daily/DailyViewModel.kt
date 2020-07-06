package com.lancer.eyelast.ui.fragment.home.daily

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lancer.eyelast.api.MainPageApiService
import com.lancer.eyelast.bean.Daily
import com.lancer.eyelast.bean.PushMessage
import com.lancer.eyelast.model.MainPageRepository
import com.lancer.eyelast.network.scheduler.BaseSubscriber
import com.lancer.eyelast.network.scheduler.OnNextWithErrorListener

class DailyViewModel(private val mainPageRepository: MainPageRepository) : ViewModel() {
    fun requestDaily(
        listener: OnNextWithErrorListener<Daily>,
        url: String = MainPageApiService.DAILY_URL
    ) {
        mainPageRepository.requestDaily(url)
            ?.subscribe(
                BaseSubscriber(object : OnNextWithErrorListener<Daily> {
                    override fun onNext(t: Daily?) {
                        listener.onNext(t)
                    }

                    override fun onError(e: Throwable?) {
                        listener.onError(e)
                    }
                })
            )
    }

}