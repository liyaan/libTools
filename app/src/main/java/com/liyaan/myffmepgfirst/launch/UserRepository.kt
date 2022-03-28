package com.liyaan.myffmepgfirst.launch

import retrofit2.Call
import retrofit2.create

class UserRepository {

    suspend fun getStringData(name:String):DataBean{
        return ObjectRetrofit.reqApi.create<ApiService>().getDataSuspend()
    }

    fun getString(name:String): Call<DataBean> {
        return ObjectRetrofit.reqApi.create<ApiService>().getDatas()
    }

    suspend fun login(username:String,password:String):BaseResp<LoginData>{
        return ObjectRetrofit.reqApi.create<ApiService>().login(username,password)
    }

    suspend fun register(username: String,password: String,repassword:String):BaseResp<RegisterData>{
        return  ObjectRetrofit.reqApi.create<ApiService>().register(username,password,repassword)
    }
}