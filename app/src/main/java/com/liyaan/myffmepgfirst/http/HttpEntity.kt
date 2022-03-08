package com.liyaan.myffmepgfirst.http

import android.os.Build
import android.util.Log
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.util.*
import kotlin.collections.HashMap
import java.io.*

//发送网络 请求
class HttpEntity {
    var map: WeakHashMap<String, String> = WeakHashMap()
    var dataParam: WeakHashMap<String, Any> = WeakHashMap()
    var mapCookie:WeakHashMap<String,String> = WeakHashMap()
    var json:String? = null
    private var mConnection: HttpURLConnection? = null
    fun url(url:String):HttpEntity{
        mConnection = HttpInstance.httpUrl(url)
        return this
    }
    @Synchronized
    fun addHeader(map: WeakHashMap<String, String>?):HttpEntity{
        Log.i("aaaaaaaaa","map size ${map?.size}")
        map?.apply {
            if (size>0){
                for ((name,value) in map){
                    Log.i(name,value)
                    mConnection?.setRequestProperty(name,value)
                }
            }
        }
        return this
    }

    fun addCookie(name:String,value:String):HttpEntity{
        mConnection?.setRequestProperty(name,value)
        return this
    }
    fun addCookie(map:HashMap<String,String>):HttpEntity{
        map?.apply {
            for ((name,value) in map){
                mConnection?.setRequestProperty(name,value)
            }
        }
        return this
    }
    @Synchronized
    fun request(method:Int):String{
        try {
            when(method){
                0->{
                    mConnection?.let {
                        it.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded")
                        it.requestMethod = "GET"
                    }
                }
                1->{
                    mConnection?.let {
                        it.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded")
                        it.requestMethod = "POST"
                        val str: String = setPingString(dataParam)
                        it.setRequestProperty("Content-Length", str.length.toString())
                        it.doInput = true // 向连接中写入数据
                        //post的方式提交实际上是留的方式提交给服务器
                        it.doOutput = true
                        val outputStream: OutputStream = it.outputStream
                        outputStream.write(str.toByteArray())
                        outputStream.flush()
                        outputStream.close()
                    }
                }
                2->{
                    mConnection?.let {
                        it.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
                        it.doInput = true // 向连接中写入数据
                        it.requestMethod = "POST"
                        it.doOutput = true
                        val wr = DataOutputStream(it.outputStream)
                        wr.writeBytes(json)
                        wr.flush()
                        wr.close()
                    }
                }
            }
            return requestString()
        }catch (e:Exception){
//            backRequest("3")
            e.printStackTrace()
            return "3"
        }finally {
            mConnection?.disconnect()
            mConnection = null
        }
        return ""
    }
    fun postParams(data:WeakHashMap<String,Any>):HttpEntity{
       this.dataParam = data
        return this
    }
    fun postJson(json:String):HttpEntity{
        this.json = json
        return this
    }
    private fun requestString():String{
        mConnection?.let {
            it.connect()
            return if (it.responseCode==200){
                inputString(it.inputStream)
            }else{
                "1"
            }
        }
        return "2"
    }

    private fun inputString(input:InputStream):String{
        val inputStream = InputStreamReader(input)
        val reader = BufferedReader(inputStream)
        var lines: String = ""
        val sbuffer = StringBuffer("")
        while (reader.readLine()?.let { lines = it } != null) {
            lines = String(lines.toByteArray())
            sbuffer.append(lines)
        }
        reader.close()
        inputStream.close()
        return lines
    }
    private fun setPingString(params:WeakHashMap<String,Any>):String{
        val sb = StringBuilder()
        // 如果有实体数据上传，则拼接实体字符串
        // 如果有实体数据上传，则拼接实体字符串
        if (params != null && !params.isEmpty()) {
            for ((key, value) in params) {
                sb.append(key).append('=').append(value).append('&')
            }
            sb.deleteCharAt(sb.length - 1)
        }
        return sb.toString()
    }
}