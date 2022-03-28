package com.liyaan.myffmepgfirst.fragment

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.liyaan.myffmepgfirst.R
import com.liyaan.myffmepgfirst.databinding.FragmentDownloadBinding
import com.liyaan.myffmepgfirst.download.DownloadManager
import com.liyaan.myffmepgfirst.download.DownloadStatus
import kotlinx.coroutines.flow.collect
import java.io.File
import com.liyaan.myffmepgfirst.exit.makeText

const val DOWN_URL = "http://download.aitifen.com/android/gs1v1-release.apk"
class DownloadFragment : Fragment() {


    private val mBinding:FragmentDownloadBinding by lazy {
        FragmentDownloadBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            context?.apply {
                val filePath = Environment.getExternalStorageDirectory()
                val file = File(filePath?.path,"gs1v1-release.apk")
                DownloadManager.download(DOWN_URL,file).collect { status->
                    when(status){
                        is DownloadStatus.Progress->{
                            mBinding.apply {
                                progressBar.progress = status.value
                                tvProgress.text = "${status.value}%"
                            }
                        }
                        is DownloadStatus.Error->{
                            makeText("下载错误")
                        }
                        is DownloadStatus.Done->{
                            mBinding.apply {
                                progressBar.progress = 100
                                tvProgress.text = "100%"
                                makeText("下载成功")
                            }
                        }
                    }
                }
            }
        }

    }
}