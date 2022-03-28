package com.liyaan.myffmepgfirst.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.liyaan.myffmepgfirst.R
import com.liyaan.myffmepgfirst.databinding.FragmentSharedFlowBinding
import com.liyaan.myffmepgfirst.flowPrectice.SharedFlowViewModel


class SharedFlowFragment : Fragment() {
    private val viewModel by viewModels<SharedFlowViewModel>()
    private val mBinding:FragmentSharedFlowBinding by lazy {
        FragmentSharedFlowBinding.inflate(layoutInflater)
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
        mBinding.apply {
            btnPause.setOnClickListener {
                viewModel.stopRefresh()
            }
            btnStart.setOnClickListener {
                viewModel.startRefresh()
            }
        }
    }

}