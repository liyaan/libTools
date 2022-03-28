package com.liyaan.myffmepgfirst.paging.net


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val instance:Retrofit by lazy {
        val inter = HttpLoggingInterceptor()
        inter.level = HttpLoggingInterceptor.Level.BODY
        Retrofit.Builder().client(OkHttpClient.Builder().addInterceptor(inter).build())
            .baseUrl("https://www.wanandroid.com/").addConverterFactory(GsonConverterFactory.create()).build()
    }
    fun <T>createApi(clazz:Class<T>):T = instance.create(clazz) as T
//    val retrofitApi: MovieApi by lazy {
//        instance.create(MovieApi::class.java)
//    }
}