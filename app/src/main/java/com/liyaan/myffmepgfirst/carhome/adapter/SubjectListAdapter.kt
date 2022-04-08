package com.liyaan.myffmepgfirst.carhome.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.liyaan.myffmepgfirst.carhome.model.SubjectDataDataX
import com.liyaan.myffmepgfirst.databinding.ItemSubjectListBinding

class SubjectListAdapter(private val context: Context)
    :PagingDataAdapter<SubjectDataDataX,BindingViewHolder>(object: DiffUtil.ItemCallback<SubjectDataDataX>(){
    override fun areItemsTheSame(oldItem: SubjectDataDataX, newItem: SubjectDataDataX): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SubjectDataDataX, newItem: SubjectDataDataX): Boolean {
        return oldItem == newItem
    }

}) {
    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        getItem(position).let {
            val binding =  holder.binding as ItemSubjectListBinding
            binding.subject = it
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = ItemSubjectListBinding.inflate(LayoutInflater.from(context),parent,false)
        return BindingViewHolder(binding)
    }
}