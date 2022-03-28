package com.liyaan.myffmepgfirst.paging.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.liyaan.myffmepgfirst.databinding.ItemListInfoBinding
import com.liyaan.myffmepgfirst.paging.model.ListDataX

class ListInfoAdapter(private val context: Context)
    :PagingDataAdapter<ListDataX,ListInfoBinding>(object:DiffUtil.ItemCallback<ListDataX>(){
    override fun areItemsTheSame(oldItem: ListDataX, newItem: ListDataX): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListDataX, newItem: ListDataX): Boolean {
        return oldItem == newItem
    }

}) {




    override fun onBindViewHolder(holder: ListInfoBinding, position: Int) {
        val dataX = getItem(position)
        dataX?.let {
            val binding = holder.binding as ItemListInfoBinding
            binding.networkImage = "https://img1.baidu.com/it/u=1308192269,3284004253&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500"
            binding.movie = it
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListInfoBinding {
        val binding = ItemListInfoBinding.inflate(LayoutInflater.from(context),parent,false)
        return ListInfoBinding(binding)
    }
}