package com.liyaan.myffmepgfirst

import android.Manifest
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.liyaan.myffmepgfirst.asp.LogClick
import com.liyaan.myffmepgfirst.asp.net.LogNet
import com.liyaan.myffmepgfirst.asp.permission.Permission
import com.liyaan.myffmepgfirst.asp.permission.PermissionDenied
import com.liyaan.myffmepgfirst.asp.permission.PermissionFailed
import com.liyaan.myffmepgfirst.http.HttpRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File


const val REQUEST_CODE = 1
class MainActivity : AppCompatActivity() {
    private val sampleTextView by lazy {
        findViewById<TextView>(R.id.sample_text)
    }
    private val mHandler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Example of a call to a native method
        val handler = Handler()
        sampleTextView.text = stringFromJNI()
        PermissionUtils.isGrantExternalRW(this, 0)
        val filePath = Environment.getExternalStorageDirectory()

        try {
            val file = File(filePath, "123456.mp4");
            Log.i("LIYAAN", "${file.length()} ${file.exists()}")
            openFile(file.absolutePath)
        }catch (e: Exception){
            Log.i("LIYAAN", "文件读取失败")
        }
//        HttpRequest.requestUrl("").get {
//            Log.i("LIYAAN",it)
//            sampleTextView.text = it
//        }
//        val json = "{\"page\":\"1\",\"page_size\":\"1\"}"
//        HttpRequest
//            .requestUrl("").requestJson(json).postJson {
//                Log.i("LIYAAN","psotJson   $it")
//                sampleTextView.text = it
//            }
//        HttpEntity.url("").addHeader("","").get {
//
//        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    private external fun stringFromJNI(): String
    private external fun openFile(url: String)
    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

    private suspend fun test(){
        delay(1000)
        Log.i("LIYAAN", "test->method")
        withContext(Dispatchers.IO){
            Log.i("LIYAAN", "test->method->IO")
        }
    }
    @LogClick
    @LogNet
    fun clickView(view: View) {
        Log.i("LIYAAN", "aaaaaaaaaaaaaaaaaaaaa")
    }
    @Permission(
        requestPermissionList = [Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CALL_PHONE],
        REQUEST_CODE
    )
    fun onSelectImage(view: View) {
        Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
    }
    @PermissionDenied(requestCode = REQUEST_CODE)
    private fun requestPermissionDenied() {
        Toast.makeText(this, "权限申请失败，不在询问", Toast.LENGTH_SHORT).show()
    }

    @PermissionFailed(requestCode = REQUEST_CODE)
    private fun requestPermissionFailed() {
        Toast.makeText(this, "用户拒绝了权限", Toast.LENGTH_SHORT).show()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}