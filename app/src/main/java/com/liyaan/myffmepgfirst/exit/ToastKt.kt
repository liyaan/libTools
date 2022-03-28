package com.liyaan.myffmepgfirst.exit

import android.content.Context
import android.widget.Toast
import com.liyaan.myffmepgfirst.App

inline fun Context.makeText(value:CharSequence?,time:Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this,value,time).show()

