package com.liyaan.myffmepgfirst.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.liyaan.myffmepgfirst.R
import com.liyaan.myffmepgfirst.databinding.FragmentUserBinding
import com.liyaan.myffmepgfirst.flowPrectice.UserAdapter
import com.liyaan.myffmepgfirst.flowPrectice.UserViewModel
import kotlinx.coroutines.flow.collect

class UserFragment : Fragment() {
    private val userViewModel by viewModels<UserViewModel>()
    private val mBinding:FragmentUserBinding by lazy {
        FragmentUserBinding.inflate(layoutInflater)
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
            btnAddUser.setOnClickListener {
                userViewModel.insert(
                    userNumber.text.toString(),userName.text.toString(),userSecondName.text.toString()
                )
            }
        }
        context?.let { it ->
            val adapter = UserAdapter(it)
            mBinding.recyclerView.adapter = adapter
            lifecycleScope.launchWhenCreated {
                userViewModel.getAll().collect {list->
                    adapter.setDataUser(list)
                }
            }
        }
    }
}