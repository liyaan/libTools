package com.liyaan.myffmepgfirst

import android.app.Application

class App:Application() {
    var app:Application? = null
    override fun onCreate() {
        super.onCreate()
        app = this
    }
}