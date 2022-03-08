package com.liyaan.myffmepgfirst.asp

/**
 * 第一步定义注解（这就是用来修饰需要打印时间方法的注解）
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class LogClick
