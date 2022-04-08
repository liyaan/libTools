package com.liyaan.myffmepgfirst.carhome.adapter

import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.liyaan.myffmepgfirst.databinding.NetworkStateItemBinding
import com.liyaan.myffmepgfirst.exit.isVisible

class NetWorkSateItemViewHolder(
    private val binding:NetworkStateItemBinding,
    val retry:()->Unit):RecyclerView.ViewHolder(binding.root) {
        fun bindData(data:LoadState){
            binding.apply {
                progress.isVisible = data is LoadState.Loading
                retryButton.isVisible = data is LoadState.Error
                retryButton.setOnClickListener { retry() }

                errorMsg.isVisible = !(data as? LoadState.Error)?.error?.message.isNullOrBlank()
                errorMsg.text = (data as? LoadState.Error)?.error?.message
            }
        }
}