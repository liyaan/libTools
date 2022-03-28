package com.liyaan.myffmepgfirst.launch

data class DataBean(
    var dataEntity: MutableList<Data>,
    var errorCode: Int,
    var errorMsg: String
)

data class Data(
    var children: List<Any>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
)

data class LoginEntity(
    val data: LoginData,
    val errorCode: Int,
    val errorMsg: String
)

data class LoginData(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val coinCount: Int,
    val collectIds: List<Any>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)
data class RegisterEntity(
    val data: RegisterData,
    val errorCode: Int,
    val errorMsg: String
)

data class RegisterData(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val coinCount: Int,
    val collectIds: List<Any>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)