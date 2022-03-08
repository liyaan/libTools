package com.liyaan.myffmepgfirst.asp.permission

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

@Aspect
class PermissionAspect {
    @Pointcut("execution(@com.liyaan.myffmepgfirst.asp.permission.Permission * *(..)) && @annotation(permission)")
    fun requestPermission(permission: Permission) {
    }
    @Around("requestPermission(permission)")
    fun permission(proceedingJoinPoint:ProceedingJoinPoint,permission:Permission){
//        val method = (proceedingJoinPoint.signature as? MethodSignature)?.method ?: return
        val aThis = proceedingJoinPoint.`this`
        val context:Context? = context(aThis)
        Log.i("aaaaa3333","22323222")
        context?.apply {

            if (permission.requestPermissionList == null ||
                permission.requestPermissionList.isEmpty()
            ) {
                return
            }
            Log.i("aaaaa3333","22323222aaa")
            val permissions = permission.requestPermissionList
            val requestCode = permission.requestCode
            PermissionActivity.start(context,permissions,requestCode,
                object:RequestCallBackListener{
                override fun permissionSuccess() {
                    try {
                        proceedingJoinPoint.proceed()
                    }catch (t:Throwable){
                        t.printStackTrace()
                    }
                }

                override fun permissionCanceled() {
                    PermissionUtils.invokeAnnotation(aThis,
                        PermissionFailed::class.java,requestCode)
                }

                override fun permissionDenied() {
                    PermissionUtils.invokeAnnotation(aThis,
                        PermissionDenied::class.java,requestCode)
                }

            })
        }

    }

    private fun context(any:Any):Context?{
        return when (any) {
            is Activity -> {
                any
            }
            is Fragment -> {
                any.requireActivity()
            }
            is View -> {
                any.context
            }
            else -> null
        }
    }
}