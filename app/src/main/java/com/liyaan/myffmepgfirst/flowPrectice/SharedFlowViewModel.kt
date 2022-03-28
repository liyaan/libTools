package com.liyaan.myffmepgfirst.flowPrectice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liyaan.myffmepgfirst.common.Event
import com.liyaan.myffmepgfirst.common.LocalEventBus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SharedFlowViewModel:ViewModel() {
    private lateinit var job:Job
    fun startRefresh(){
       job =  viewModelScope.launch(Dispatchers.IO) {
            while (true){
                LocalEventBus.postEvent(Event(System.currentTimeMillis()))
            }
        }
    }

    fun stopRefresh(){
        job.cancel()
    }
}