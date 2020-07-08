package com.lancer.eyelast.ui.fragment.community.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lancer.eyelast.model.MainPageRepository

/**
 * @author lancer
 * @des
 * @Date 2020/7/8 8:50
 */
class FollowViewModelFactory(val repository: MainPageRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FollowViewModel(repository) as T
    }
}