package com.liyaan.myffmepgfirst.carhome

import com.liyaan.myffmepgfirst.carhome.model.SubjectListEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SubjectService {

    @GET("project/list/{page}/json")
    suspend fun list(@Path("page")page:Int,@Query("cid")cid:String): SubjectListEntity
}