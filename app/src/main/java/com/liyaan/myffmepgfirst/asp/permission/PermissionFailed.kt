package com.liyaan.myffmepgfirst.asp.permission


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class PermissionFailed(val requestCode:Int)
