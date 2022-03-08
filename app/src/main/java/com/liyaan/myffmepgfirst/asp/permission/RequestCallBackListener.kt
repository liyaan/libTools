package com.liyaan.myffmepgfirst.asp.permission

interface RequestCallBackListener {
    fun permissionSuccess()

    fun permissionCanceled()

    fun permissionDenied()
}