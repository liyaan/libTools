package com.liyaan.myffmepgfirst.carhome.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.liyaan.myffmepgfirst.R

@BindingAdapter("bindingImage")
fun bindingAvator(imageView:ImageView,url:String?){
//    val url = url?:"https://img1.baidu.com/it/u=1866209702,1570820536&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=889"
    imageView.load(url){
        crossfade(true)
        placeholder(R.mipmap.ic_launcher_round)
    }
}