package com.liyaan.myffmepgfirst.asp.permission

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class PermissionActivity:AppCompatActivity() {
    companion object{
        private val REQUEST_PERMISSIONS_LIST = "requestPermissions"
        private val REQUEST_CODE = "requestCode"
        private var requestCallBack: RequestCallBackListener? = null

        private val REQUEST_CODE_DEFAULT = -1

        fun start(
            context: Context,
            permissions: Array<String>,
            requestCode: Int,
            requestCallBackListener: RequestCallBackListener
        ) {
            requestCallBack = requestCallBackListener
            val bundle = Bundle()
            bundle.putStringArray(REQUEST_PERMISSIONS_LIST, permissions)
            bundle.putInt(REQUEST_CODE, requestCode)
            val intent = Intent()
            intent.putExtras(bundle)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.setClass(context, PermissionActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.apply {
            val permissionList: Array<out String> = getStringArrayExtra(REQUEST_PERMISSIONS_LIST) as Array<out String>
            val requestCode = getIntExtra(REQUEST_CODE, REQUEST_CODE_DEFAULT)
            Log.i("aaaa222","asdfdsa")
            if (permissionList == null || requestCode == REQUEST_CODE_DEFAULT || requestCallBack==null){
                finish()
                return
            }
            if(PermissionUtils.hasPermissionRequest(this@PermissionActivity, permissionList)){
                requestCallBack?.permissionSuccess()
                return
            }
            ActivityCompat.requestPermissions(this@PermissionActivity, permissionList, requestCode)
            return
        }
        this.finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (PermissionUtils.hasPermissionRequest(this, permissions)){
            requestCallBack?.permissionSuccess()
            this.finish()
            return
        }
        if (PermissionUtils.shouldShowRequestPermissionRationale(this, permissions)) {
            requestCallBack?.permissionDenied()
            this.finish()
            return
        }
        requestCallBack?.permissionCanceled()
        this.finish();
    }
    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }
}