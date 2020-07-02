package com.lancer.eyelast.ui.fragment.notifications.letter

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.databinding.FragmentLetterBinding

class LetterFragment : BaseFragment<FragmentLetterBinding>() {
    init {
        name = LetterFragment::class.java.simpleName
    }

    companion object {
        fun newInstance() = LetterFragment()
    }

    private lateinit var viewModel: LetterViewModel

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initLayout(): Int = R.layout.fragment_letter

}