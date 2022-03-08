package com.liyaan.myffmepgfirst.asp.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.ArrayList


object PermissionUtils {
    fun hasPermissionRequest(context: Context, permissionArray: Array<out String>): Boolean {
        for (permission in permissionArray) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.i("aaaaa",permission)
                return false
            }
            Log.i("aaaaa11", permission)
        }

        return true
    }

    fun shouldShowRequestPermissionRationale(
        activity: Activity,
        permissions: Array<out String>
    ): Boolean {
        for (permission in permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true
            }
        }
        return false
    }


    fun invokeAnnotation(`object`: Any, annotationClass: Class<*>, requestCode: Int) {
        // 获取 object 的 Class对象
        val objectClass: Class<*> = `object`.javaClass

        // 遍历 所有的方法
        val methods: Array<Method> = objectClass.declaredMethods
        for (method in methods) {
            method.isAccessible = true // 让虚拟机，不要去检测 private

            // 判断方法 是否有被 annotationClass注解的方法
            val annotationPresent: Boolean = method.isAnnotationPresent(annotationClass as Class<out Annotation>)
            if (annotationPresent) {
                // 当前方法 代表包含了 annotationClass注解的
                val annotation: Annotation = method.getAnnotation(annotationClass) as Annotation
                var mRequestCode = -1
                if (annotationClass == PermissionFailed::class.java) {
                    mRequestCode = (annotation as PermissionFailed).requestCode
                } else if (annotationClass == PermissionDenied::class.java) {
                    mRequestCode = (annotation as PermissionDenied).requestCode
                }
                try {
                    if (mRequestCode == requestCode) {
                        method.invoke(`object`)
                    }
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }
            }
        }
    }
}