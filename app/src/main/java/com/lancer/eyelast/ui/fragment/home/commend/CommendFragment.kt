package com.lancer.eyelast.ui.fragment.home.commend

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lancer.eyelast.R

/**
 * 推荐界面
 */
class CommendFragment : Fragment() {

    companion object {
        fun newInstance() = CommendFragment()
    }

    private lateinit var viewModel: CommendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_commend, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CommendViewModel::class.java)
        // TODO: Use the ViewModel
    }

}