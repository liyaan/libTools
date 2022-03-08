package com.liyaan.myffmepgfirst.http

import java.util.*

interface HttpInterface {

    fun addHeader():WeakHashMap<String,String>?

    fun addCookie():WeakHashMap<String,String>?

    suspend fun getRequest(url:String):String
    suspend fun getRequest(url:String,map:WeakHashMap<String,Any>):String
    suspend fun postParam(url:String,map:WeakHashMap<String,Any>):String

    suspend fun postJson(url:String,json:String):String
}