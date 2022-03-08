package com.liyaan.myffmepgfirst.asp.permission



//annotation class Permission(val requestPermissionList: Array<String>, val requestCode: Int)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Permission(
    /**
     * 申请权限列表
     */
    val requestPermissionList: Array<String>,
    /**
     * 请求码
     */
    val requestCode: Int
)
