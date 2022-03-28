package com.liyaan.myffmepgfirst.paging.net

import com.liyaan.myffmepgfirst.paging.model.ListEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("article/list/{page}/json")
    suspend fun list(@Path("page")page:Int):ListEntity
}