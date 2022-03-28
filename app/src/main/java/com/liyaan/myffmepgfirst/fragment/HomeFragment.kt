package com.liyaan.myffmepgfirst.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.liyaan.myffmepgfirst.R
import com.liyaan.myffmepgfirst.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val mBinding:FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
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
            btnFlowAndDownload.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_downloadFragment2)
            }
            btnFlowAndRoom.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_userFragment2)
            }
            btnFlowAndRetrofit.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_retrofitFragment2)
            }
            btnFlowAndFlow.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_numberFragment)
            }
            btnFlowSharedFlow.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_sharedFlowFragment)
            }
        }
    }

}