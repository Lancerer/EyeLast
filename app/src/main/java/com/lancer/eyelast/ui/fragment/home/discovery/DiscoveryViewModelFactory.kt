package com.lancer.eyelast.ui.fragment.home.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lancer.eyelast.model.MainPageRepository

/**
 * @author lancer
 * @des
 * @Date 2020/7/8 15:28
 */
class DiscoveryViewModelFactory(private val repository: MainPageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DiscoveryViewModel(repository) as T
    }
}