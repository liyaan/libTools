package com.liyaan.myffmepgfirst.carhome.init

import android.content.Context
import androidx.startup.Initializer

class AppInitializer:Initializer<Unit> {
    override fun create(context: Context) {
        AppHelper.init(context)
    }
    //指定依赖库初始化的顺序A->B->C
    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}