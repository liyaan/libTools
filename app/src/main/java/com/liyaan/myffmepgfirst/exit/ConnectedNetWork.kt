package com.liyaan.myffmepgfirst.exit

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo

fun Context.isConnectedNetWork():Boolean = run {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetWork:NetworkInfo? = cm.activeNetworkInfo
    activeNetWork?.isConnectedOrConnecting == true
}