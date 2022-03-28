package com.liyaan.myffmepgfirst.paging.adapter

import android.graphics.Color
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.liyaan.myffmepgfirst.R
import com.squareup.picasso.Picasso

class ImageViewBindingAdapter {
    companion object{
        @JvmStatic
        @BindingAdapter("image")
        fun setImage(img:ImageView,url:String){
            if (!TextUtils.isEmpty(url)){
                Picasso.get().load(url)
                    .placeholder(R.drawable.ic_launcher_background).into(img)
            }else{
                img.setBackgroundColor(Color.GRAY)
            }
        }
    }

}