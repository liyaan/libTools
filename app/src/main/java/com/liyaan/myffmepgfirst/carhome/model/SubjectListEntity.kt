package com.liyaan.myffmepgfirst.carhome.model

data class SubjectListEntity(
    val data: SubjectData,
    val errorCode: Int,
    val errorMsg: String
)

data class SubjectData(
    val curPage: Int,
    val datas: List<SubjectDataDataX>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class SubjectDataDataX(
    val id:Int,
    val title:String,
    val link:String,
    val envelopePic:String,
    val projectLink:String,
)

data class Tag(
    val name: String,
    val url: String
)