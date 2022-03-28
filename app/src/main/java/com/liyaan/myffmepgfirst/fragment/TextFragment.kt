package com.liyaan.myffmepgfirst.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.liyaan.myffmepgfirst.R
import com.liyaan.myffmepgfirst.common.LocalEventBus
import com.liyaan.myffmepgfirst.databinding.FragmentTextBinding
import kotlinx.coroutines.flow.collect


class TextFragment : Fragment() {
    private val mBinding:FragmentTextBinding by lazy {
        FragmentTextBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            LocalEventBus.events.collect {
                mBinding.tvTime.text = it.timestamp.toString()
            }
        }
    }

}