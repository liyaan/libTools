package com.liyaan.myffmepgfirst.asp.net

import android.app.Activity
import android.content.Context
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import android.net.NetworkInfo

import android.net.ConnectivityManager
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment


@Aspect
class JudgeAopNet {

    @Pointcut("execution(@com.liyaan.myffmepgfirst.asp.net.LogNet * *(..))")
    fun printFunTime() {}
    @Around("printFunTime()")
    @Throws(Throwable::class)
    fun printFunNet(point: ProceedingJoinPoint){
        //获取调用方法定义
        val method = (point.signature as? MethodSignature)?.method ?: return
        if (method.isAnnotationPresent(LogNet::class.java)){
            val obj = point.`this`
            val context = context(obj)
            context?.apply {
                if (!isNetworkAvailable(this)){
                    Toast.makeText(this,"请检查网络",Toast.LENGTH_LONG).show()
                    return
                }
                point.proceed()
            }
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

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    private fun isNetworkAvailable(context: Context): Boolean {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            // 获取NetworkInfo对象
            val networkInfo = connectivityManager.allNetworkInfo
            if (networkInfo != null && networkInfo.isNotEmpty()) {
                for (i in networkInfo.indices) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
        }
        return false
    }
}