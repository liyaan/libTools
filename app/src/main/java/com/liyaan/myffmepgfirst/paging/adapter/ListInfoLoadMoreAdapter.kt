package com.liyaan.myffmepgfirst.paging.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.liyaan.myffmepgfirst.databinding.ListInfoMoreBinding
import com.liyaan.myffmepgfirst.flowPrectice.UserAdapter

class ListInfoLoadMoreAdapter(private val context: Context):LoadStateAdapter<ListInfoBinding>() {
    override fun onBindViewHolder(holder: ListInfoBinding, loadState: LoadState) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ListInfoBinding {
        val binding = ListInfoMoreBinding.inflate(LayoutInflater.from(context),parent,false)
        return ListInfoBinding(binding)
    }
}