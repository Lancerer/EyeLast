package com.lancer.eyelast.ui.fragment.home.daily

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lancer.eyelast.R

/**
 * 日报
 */
class DailyFragment : Fragment() {

    companion object {
        fun newInstance() = DailyFragment()
    }

    private lateinit var viewModel: DailyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_daily, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DailyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}