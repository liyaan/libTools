package com.liyaan.myffmepgfirst.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val instance:Retrofit by lazy {
        Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl("https://wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    val retrofitApi:RetrofitApi by lazy {
        instance.create(RetrofitApi::class.java)
    }
}