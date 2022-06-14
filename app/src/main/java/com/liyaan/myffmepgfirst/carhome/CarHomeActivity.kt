package com.liyaan.myffmepgfirst.carhome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.liyaan.myffmepgfirst.MainActivity
import com.liyaan.myffmepgfirst.asp.permission.PermissionActivity
import com.liyaan.myffmepgfirst.carhome.adapter.FooterAdapter
import com.liyaan.myffmepgfirst.carhome.adapter.SubjectListAdapter
import com.liyaan.myffmepgfirst.carhome.viewmodel.SubjectViewModel
import com.liyaan.myffmepgfirst.databinding.ActivityCarHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CarHomeActivity:AppCompatActivity() {
    private val mBinding:ActivityCarHomeBinding by lazy {
        ActivityCarHomeBinding.inflate(layoutInflater)
    }

    private val mViewModel:SubjectViewModel by viewModels()
    private val adapter:SubjectListAdapter by lazy {
        SubjectListAdapter(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.recyclerView.adapter = adapter.withLoadStateFooter(FooterAdapter(adapter,this))
        mViewModel.data.observe(this){
            adapter.submitData(lifecycle,it)
            mBinding.swiperRefresh.isEnabled = false
        }
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                mBinding.swiperRefresh.isRefreshing = it.refresh is LoadState.Loading
            }
        }
        mBinding.appClick.setOnClickListener {
            val intent = Intent(this@CarHomeActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
    }
}