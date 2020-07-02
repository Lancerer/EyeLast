package com.lancer.eyelast.ui.fragment.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lancer.eyelast.R

class CommunityFragment : Fragment() {

    private lateinit var communityViewModel: CommunityViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        communityViewModel =
                ViewModelProviders.of(this).get(CommunityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_community, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        communityViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}