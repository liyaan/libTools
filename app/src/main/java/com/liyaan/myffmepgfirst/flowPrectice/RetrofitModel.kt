package com.liyaan.myffmepgfirst.flowPrectice

import android.app.Application
import android.widget.Toast
import android.widget.Toast.makeText
import android.widget.Toolbar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.liyaan.myffmepgfirst.bean.DataX
import com.liyaan.myffmepgfirst.net.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
class RetrofitModel(app:Application):AndroidViewModel(app) {
    val datas = MutableLiveData<List<DataX>>()
    fun searchAuthor(author:String){
        viewModelScope.launch {
            flow {
                val entity = RetrofitClient.retrofitApi.searchArticles(author)
                emit(entity)
            }.flowOn(Dispatchers.IO).catch { e->e.printStackTrace() }.collect {
                if (it.errorCode==0){
                    datas.value = it.data.datas
                }else{
                    makeText(getApplication(),it.errorMsg,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}