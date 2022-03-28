package com.liyaan.myffmepgfirst.launch

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ObjectRetrofit {
    val BASE_URL = "https://www.wanandroid.com/"
    val reqApi by lazy {
        val logging = HttpLoggingInterceptor();
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClientBuilder =  OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(LoggingInterceptor())
            .addInterceptor(logging).build()
        val retrofit = Retrofit.Builder()
            .client(httpClientBuilder)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create()).build()
        retrofit
    }


}