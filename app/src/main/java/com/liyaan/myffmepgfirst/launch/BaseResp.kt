package com.liyaan.myffmepgfirst.launch

import android.widget.Toast
import com.liyaan.myffmepgfirst.App
import java.lang.RuntimeException

data class BaseResp <T>(
    var errorCode: Int = 0,
    var errorMsg: String = "",
    var data: T
)

fun <T> BaseResp<T>.dataConvert() :T?{
    if (errorCode == 0) {
        return data
    } else {
        Toast.makeText(App.app,errorMsg,Toast.LENGTH_SHORT).show()
        return null
    }
}