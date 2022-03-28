package com.liyaan.myffmepgfirst.launch

import android.text.TextUtils
import android.util.Log
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
         val response = chain.proceed(request)
        if (response!=null){
            val headers: Headers = response.headers
            val cookies: List<String> = headers.values("Set-Cookie")
            if (cookies!=null && cookies.isNotEmpty()){
                val session = cookies.get(0);
                if (!TextUtils.isEmpty(session)) {
                    val size = session.length;
                    val i = session.indexOf(";");
                    if (i in 0 until size) {
                        val result = session.substring(0, i)
                        Log.i("aaaaa cookie",result)
//                        MyApplication.setOkhttpCookie(result);
                    }
                }
            }
        }

        return  response
    }
}