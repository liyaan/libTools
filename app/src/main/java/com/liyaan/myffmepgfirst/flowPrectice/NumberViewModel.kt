package com.liyaan.myffmepgfirst.flowPrectice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.flow.MutableStateFlow

class NumberViewModel(app:Application):AndroidViewModel(app) {
    val number = MutableStateFlow(0)


    fun numberPlus(){
        number.value ++
    }

    fun numberMinus(){
        number.value --
    }
}