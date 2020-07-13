package com.lancer.eyelast.ui.fragment.notifications.letter

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseFragment
import com.lancer.eyelast.databinding.FragmentLetterBinding
import com.lancer.eyelast.ui.activity.login.LoginActivity

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initData() {
        binding.login.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
    }

    override fun initLayout(): Int = R.layout.fragment_letter

}