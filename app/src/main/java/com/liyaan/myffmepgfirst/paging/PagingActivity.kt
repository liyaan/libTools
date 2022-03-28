package com.liyaan.myffmepgfirst.paging

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.liyaan.myffmepgfirst.databinding.ActivityPagingBinding
import com.liyaan.myffmepgfirst.paging.adapter.ListInfoAdapter
import com.liyaan.myffmepgfirst.paging.adapter.ListInfoLoadMoreAdapter
import com.liyaan.myffmepgfirst.paging.viewmodel.ListViewModel
import kotlinx.coroutines.flow.collectLatest

class PagingActivity:AppCompatActivity() {
    private val mBinding:ActivityPagingBinding by lazy {
        ActivityPagingBinding.inflate(layoutInflater)
    }
    private val lisInfoAdapter:ListInfoAdapter by lazy {
        ListInfoAdapter(this)
    }
    private val viewModel by viewModels<ListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.apply {
            recyclerView.adapter = lisInfoAdapter.withLoadStateFooter(ListInfoLoadMoreAdapter(this@PagingActivity))
            swipeRefreshLayout.setOnRefreshListener {
                lisInfoAdapter.refresh()
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.loadList().collectLatest {
                lisInfoAdapter.submitData(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            lisInfoAdapter.loadStateFlow.collectLatest {
                mBinding.swipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading
            }
        }
    }
}