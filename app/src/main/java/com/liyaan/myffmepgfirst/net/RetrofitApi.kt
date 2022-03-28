package com.liyaan.myffmepgfirst.net

import com.liyaan.myffmepgfirst.bean.RetrofitBean
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("/article/list/0/json")
    suspend fun searchArticles(@Query("author")author:String): RetrofitBean
}