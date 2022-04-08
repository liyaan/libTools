package com.liyaan.myffmepgfirst.carhome.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.liyaan.myffmepgfirst.databinding.NetworkStateItemBinding

class FooterAdapter(
    val adapter: SubjectListAdapter,
    val context: Context
) :LoadStateAdapter<NetWorkSateItemViewHolder>(){
    override fun onBindViewHolder(holder: NetWorkSateItemViewHolder, loadState: LoadState) {
        val params = holder.itemView.layoutParams
        if (params is StaggeredGridLayoutManager.LayoutParams){
            params.isFullSpan = true
        }
        holder.bindData(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetWorkSateItemViewHolder {
        val binding = NetworkStateItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return NetWorkSateItemViewHolder(binding){
            adapter.retry()
        }
    }
}