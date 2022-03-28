package com.liyaan.myffmepgfirst.flowPrectice

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.liyaan.myffmepgfirst.bean.DataX
import com.liyaan.myffmepgfirst.databinding.ItemRetrofitBinding
import com.liyaan.myffmepgfirst.databinding.ItemUserBinding
import com.liyaan.myffmepgfirst.db.User

class RetrofitAdapter(private val context: Context): RecyclerView.Adapter<RetrofitAdapter.BindingViewHolder>() {
    private val dataUser = ArrayList<DataX>()

    fun setDataUser(data:List<DataX>){
        this.dataUser.clear()
        this.dataUser.addAll(data)
        Log.i("aaaaaa",dataUser.toString())
        notifyDataSetChanged()
    }

    inner class BindingViewHolder(val binding:ViewBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
       val binding =  ItemRetrofitBinding.inflate(LayoutInflater.from(context),parent,false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = dataUser[position]
        val binding = holder.binding as ItemRetrofitBinding
        Log.i("aaaa","${item.chapterName} - ${item.title} - ${item.niceShareDate}")
        binding.textView3.text = "${item.chapterName} - ${item.title} - ${item.niceShareDate}"
    }

    override fun getItemCount(): Int {
        return dataUser.size
    }
}