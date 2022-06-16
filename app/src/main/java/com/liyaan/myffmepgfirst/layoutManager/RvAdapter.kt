package com.liyaan.myffmepgfirst.layoutManager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.liyaan.myffmepgfirst.R

class RvAdapter(context:Context,paths:MutableList<String>): RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    private val mContext = context
    private val mPaths = paths
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPath = itemView.findViewById<ImageView>(R.id.imageView3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.item_rv_adapter,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holder = holder as ViewHolder
        holder.imgPath.load(mPaths[position])
        holder.imgPath.setOnClickListener {
            listener.invoke(position,mPaths[position])
        }
    }

    override fun getItemCount(): Int = mPaths.size


    fun onItemClick(click:(Int,String)->Unit) {
        this.listener = click
    }
    private lateinit var listener:(Int, String)->Unit
}