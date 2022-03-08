package com.liyaan.myffmepgfirst.asp

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

@Aspect
class JudgeAopClick {
    private var clickTime:Long = 0
    private val FAST_CLICK_DELAY_TIME = 1000
    /**
     * 第三步定义切点 使用@Pointcut修饰一个用@Aspect修饰的类中的方法
     * @Pointcut 中execution的参数表示的是所有带PrintFunTime注解的方法
    此处由于只是一个简单的示例所以只用了这一种方式，若需要适配更多的方法可以参考
    [https://blog.csdn.net/zlmrche/article/details/79643801](https://blog.csdn.net/zlmrche/article/details/79643801)
    这里面有更详细的说明
     */
    @Pointcut("execution(@com.liyaan.myffmepgfirst.asp.LogClick * *(..))")
    fun printFunTime() {}

    /**
     * 第四步关联切点与处理逻辑方法 使用@Around修饰定义的方法，参数为第三步的切点方法
     *
     */
    @Around("printFunTime()")
    @Throws(Throwable::class)
    fun aroundPrintFunTime(point: ProceedingJoinPoint) {
        //获取调用方法定义
        val method = (point.signature as? MethodSignature)?.method ?: return
        //判断方法上的注解，如果方法上有定义的注解PrintFunTime 打印方法调用时间
        if (method.isAnnotationPresent(LogClick::class.java)) {
            val startTime = System.currentTimeMillis()
            if ((startTime-clickTime)<FAST_CLICK_DELAY_TIME) return
            println("printFunTime method ${method.name}  time = " +
                    "${System.currentTimeMillis() - startTime}")
            clickTime = startTime
            point.proceed()
        }

    }
}