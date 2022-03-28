package com.liyaan.myffmepgfirst.flowPrectice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.liyaan.myffmepgfirst.databinding.ActivityFlowBinding

class FlowPrecticeActivity:AppCompatActivity() {
    private val mBinding:ActivityFlowBinding by lazy {
        ActivityFlowBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }
}