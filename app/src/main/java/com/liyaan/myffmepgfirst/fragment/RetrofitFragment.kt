package com.liyaan.myffmepgfirst.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.liyaan.myffmepgfirst.R
import com.liyaan.myffmepgfirst.databinding.FragmentRetrofitBinding
import com.liyaan.myffmepgfirst.exit.textWatcherFlow
import com.liyaan.myffmepgfirst.flowPrectice.RetrofitAdapter
import com.liyaan.myffmepgfirst.flowPrectice.RetrofitModel
import kotlinx.coroutines.flow.collect


class RetrofitFragment : Fragment() {
    private val viewModel by viewModels<RetrofitModel>()
    private val retrofitFragment:FragmentRetrofitBinding by lazy {
        FragmentRetrofitBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return retrofitFragment.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            retrofitFragment.etSearch.textWatcherFlow().collect {
                Log.i("aaaaa",it)
                viewModel.searchAuthor(it)
            }
        }
        context?.let {
            val adapter = RetrofitAdapter(it)
            retrofitFragment.recyclerView.adapter = adapter
            viewModel.datas.observe(viewLifecycleOwner,{ datas->
                adapter.setDataUser(datas)
            })
        }

    }

}