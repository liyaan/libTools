package com.liyaan.myffmepgfirst.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.liyaan.myffmepgfirst.R
import com.liyaan.myffmepgfirst.databinding.FragmentNumberBinding
import com.liyaan.myffmepgfirst.flowPrectice.NumberViewModel
import kotlinx.coroutines.flow.collect


class NumberFragment : Fragment() {
    private val viewModel by viewModels<NumberViewModel>()
    private val mBinding:FragmentNumberBinding by lazy {
        FragmentNumberBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding.apply {
            btnPlus.setOnClickListener {
                viewModel.numberPlus()
            }

            btnMinus.setOnClickListener {
                viewModel.numberMinus()
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.number.collect {
                mBinding.fragmentNumberTv.text = "$it"
            }
        }
    }
}