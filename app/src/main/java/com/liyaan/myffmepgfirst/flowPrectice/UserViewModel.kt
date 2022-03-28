package com.liyaan.myffmepgfirst.flowPrectice

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liyaan.myffmepgfirst.db.AppDatabase
import com.liyaan.myffmepgfirst.db.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class UserViewModel(app:Application):AndroidViewModel(app) {

    fun insert(uid:String,firstName:String,lastName:String){
        viewModelScope.launch {
            AppDatabase.getInstance(getApplication())
                .userDao()
                .insert(User(uid.toInt(),firstName,lastName))
            Log.i("aaaa","${uid.toInt()},$firstName,$lastName")
        }
    }
    fun getAll():Flow<List<User>>{
        return AppDatabase.getInstance(getApplication())
            .userDao().getAll().catch { e->e.printStackTrace()
            }.flowOn(Dispatchers.IO)
    }
}