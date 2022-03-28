package com.liyaan.myffmepgfirst

import android.app.Application

class App:Application() {
    companion object{
        var app:Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}