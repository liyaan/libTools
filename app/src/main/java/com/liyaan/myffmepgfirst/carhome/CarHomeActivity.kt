package com.liyaan.myffmepgfirst.carhome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.liyaan.myffmepgfirst.databinding.ActivityCarHomeBinding

class CarHomeActivity:AppCompatActivity() {
    private val mBinding:ActivityCarHomeBinding by lazy {
        ActivityCarHomeBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }
}