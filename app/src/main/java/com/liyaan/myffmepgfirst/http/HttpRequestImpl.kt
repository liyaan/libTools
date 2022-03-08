package com.liyaan.myffmepgfirst.http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class HttpRequestImpl:HttpInterface {
    override fun addHeader(): WeakHashMap<String, String>? {
        val map = WeakHashMap<String,String>()
        map["version"] = "3.8.0"
        map["symbol"] = "2"
        map["token"] = "a212ac9a-278b-48d9-93e8-b5c2f2e1b3f8"
        return map
    }

    override fun addCookie(): WeakHashMap<String, String>? {
        return null
    }

    override suspend fun getRequest(url: String): String {
        return  withContext(Dispatchers.IO){
            HttpEntity().url(url).addHeader(addHeader()).request(0)
        }
    }

    override suspend fun getRequest(url: String, map: WeakHashMap<String, Any>): String {
        val sb = StringBuilder(url)
        map?.let {
            if (it.size>0){
                sb.append("?")
                for ((name,value) in map){
                    sb.append(name).append("=").append(value).append("&")
                }
                sb.deleteCharAt(sb.length - 1)
            }
        }
        return  withContext(Dispatchers.IO){
            HttpEntity().url(sb.toString()).addHeader(addHeader()).request(0)
        }
    }

    override suspend fun postParam(url: String, map: WeakHashMap<String, Any>): String {
        return withContext(Dispatchers.IO){
            HttpEntity().url(url).addHeader(addHeader())
                .postParams(map).request(1)
        }
    }

    override suspend fun postJson(url: String, json: String): String {
        return withContext(Dispatchers.IO){
            HttpEntity().url(url).addHeader(addHeader()).postJson(json)
                .request(2)
        }
    }

}