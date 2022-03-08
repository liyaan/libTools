package com.liyaan.myffmepgfirst.http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


object HttpRequest {
    private var httpReq:HttpInterface = HttpRequestImpl()
    private var url:String? = null
    private var map:WeakHashMap<String,Any>? = null
    private var json:String? = null
    fun httpRequest(http:HttpInterface){
        this.httpReq = http
    }

    fun requestUrl(url:String):HttpRequest{
        this.url = url
        return this
    }

    fun requestMap(map:WeakHashMap<String,Any>?):HttpRequest{
        this.map = map
        return this
    }

    fun requestJson(json:String):HttpRequest{
        this.json = json
        return this
    }


    fun get(method:(String)->Unit){
        url?.let {
            GlobalScope.launch(Dispatchers.Main) {
                method(httpReq.getRequest(it))
            }
        }
    }

    fun getMap(method:(String)->Unit){
        url?.let {
            GlobalScope.launch(Dispatchers.Main){
                map?.apply {
                    method(httpReq.getRequest(it,this))
                }
            }
        }
    }

    fun postMap(method:(String)->Unit){
        url?.let {
            map?.let { map->
                if (map.size>0){
                    GlobalScope.launch(Dispatchers.Main){
                        method(httpReq.postParam(it,map))
                    }
                }
            }
        }
    }

    fun postJson(method:(String)->Unit){
        url?.let { url->
            json?.let{ json->
                GlobalScope.launch(Dispatchers.Main){
                    method(httpReq.postJson(url, json))
                }
            }
        }
    }
}