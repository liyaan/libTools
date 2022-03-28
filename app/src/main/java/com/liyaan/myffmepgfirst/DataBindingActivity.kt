package com.liyaan.myffmepgfirst

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.liyaan.myffmepgfirst.databinding.ActivityLayoutDatabindingBinding
import com.liyaan.myffmepgfirst.launch.MainViewModel

class DataBindingActivity:AppCompatActivity() {
    private val mainViewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil
            .setContentView<ActivityLayoutDatabindingBinding>(this,R.layout.activity_layout_databinding)

        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this

        binding.button.setOnClickListener {
            Log.i("aaaaa","button start")
            mainViewModel.getStringData("董桂君")
            mainViewModel.login("d_123","123456")
//            mainViewModel.getStringDataOne("董桂君")
        }

    }
}