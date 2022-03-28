package com.liyaan.myffmepgfirst.launch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel:ViewModel() {
    val dataLiveData = MutableLiveData<DataBean>()
    val dataLiveDataOne = MutableLiveData<DataBean>()
    val loginEntity = MutableLiveData<LoginData>()

    private val repository = UserRepository()

    fun getStringData(name:String){
        viewModelScope.launch {
            dataLiveData.value = repository.getStringData(name)
        }
    }
    fun login(username:String,password:String){
        viewModelScope.launch {
            loginEntity.value = repository.login(username,password).dataConvert()
//            Log.i("aaaaa","${loginEntity.value!!.errorCode}")
            if (loginEntity.value==null){
                val registr = repository.register(username,password,password).dataConvert()
                if (registr!=null){
                    loginEntity.value = repository.login(username,password).dataConvert()
                }
            }
        }
    }
}