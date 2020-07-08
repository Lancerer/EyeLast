package com.lancer.eyelast.ui.fragment.community.recommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lancer.eyelast.model.MainPageRepository

/**
 * @author lancer
 * @des
 * @Date 2020/7/8 10:02
 */
class RecommendViewModelFactory(private val repository: MainPageRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RecommendViewModel(repository) as T
    }
}