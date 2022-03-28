package com.liyaan.myffmepgfirst.launch

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("wxarticle/chapters/json")
    fun getDatas() : Call<DataBean>

    @GET("wxarticle/chapters/json")
    suspend fun getDataSuspend() : DataBean

    @POST("user/login")
    @FormUrlEncoded
    suspend fun login(@Field("username")username:String,
                      @Field("password")password:String):BaseResp<LoginData>
    @POST("user/register")
    @FormUrlEncoded
    suspend fun register(@Field("username")username:String,
                         @Field("password")password:String,
                         @Field("repassword")repassword:String):BaseResp<RegisterData>
}