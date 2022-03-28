package com.liyaan.myffmepgfirst.flowPrectice

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.liyaan.myffmepgfirst.databinding.ItemUserBinding
import com.liyaan.myffmepgfirst.db.User

class UserAdapter(private val context: Context): RecyclerView.Adapter<UserAdapter.BindingViewHolder>() {
    private val dataUser = ArrayList<User>()

    fun setDataUser(data:List<User>){
        this.dataUser.clear()
        this.dataUser.addAll(data)
        Log.i("aaaaaa",dataUser.toString())
        notifyDataSetChanged()
    }

    inner class BindingViewHolder(val binding:ViewBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
       val binding =  ItemUserBinding.inflate(LayoutInflater.from(context),parent,false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = dataUser[position]
        val binding = holder.binding as ItemUserBinding
        binding.textView2.text = "${item.uid} - ${item.firstName} - ${item.lastName}"
    }

    override fun getItemCount(): Int {
        return dataUser.size
    }
}