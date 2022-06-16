package com.liyaan.myffmepgfirst

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.liyaan.myffmepgfirst.layoutManager.CenterLayoutManager
import com.liyaan.myffmepgfirst.layoutManager.GalleryItemDecoration
import com.liyaan.myffmepgfirst.layoutManager.RvAdapter

class MainRecyclerActivity: AppCompatActivity(){
    private val mRecyclerView:RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }
    private val mLinearSnapHelper:LinearSnapHelper by lazy {
        LinearSnapHelper()
    }
    private val mPathList = ArrayList<String>()
    private val mRvAdapter:RvAdapter by lazy {
        RvAdapter(this,mPathList)
    }
    private val mCenterLayoutManager:CenterLayoutManager by lazy{
        CenterLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    }
    private var nowPosition:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_recycler_layout)
        mPathList.add("https://pics6.baidu.com/feed/0b46f21fbe096b63df379948e1a0b943ebf8ac13.jpeg?token=98eac0d5f10e3043580add02ff8c06e7")
        mPathList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2F69%2F6a%2F45%2F696a45427403b660a5cbf8dfe6ba9567.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1657955420&t=939df2e364a92c67130fc13af5fbfa53")
        mPathList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.2qqtouxiang.com%2Fpic%2FBZ7495_05.jpg&refer=http%3A%2F%2Fimg.2qqtouxiang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1657955420&t=da780e39ee7f048291644be9d3d8eed5")
        mPathList.add("https://pics6.baidu.com/feed/0b46f21fbe096b63df379948e1a0b943ebf8ac13.jpeg?token=98eac0d5f10e3043580add02ff8c06e7")
        mPathList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2F69%2F6a%2F45%2F696a45427403b660a5cbf8dfe6ba9567.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1657955420&t=939df2e364a92c67130fc13af5fbfa53")
        mPathList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.2qqtouxiang.com%2Fpic%2FBZ7495_05.jpg&refer=http%3A%2F%2Fimg.2qqtouxiang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1657955420&t=da780e39ee7f048291644be9d3d8eed5")
        mPathList.add("https://pics6.baidu.com/feed/0b46f21fbe096b63df379948e1a0b943ebf8ac13.jpeg?token=98eac0d5f10e3043580add02ff8c06e7")
        mPathList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2F69%2F6a%2F45%2F696a45427403b660a5cbf8dfe6ba9567.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1657955420&t=939df2e364a92c67130fc13af5fbfa53")
        mPathList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.2qqtouxiang.com%2Fpic%2FBZ7495_05.jpg&refer=http%3A%2F%2Fimg.2qqtouxiang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1657955420&t=da780e39ee7f048291644be9d3d8eed5")
        mPathList.add("https://pics6.baidu.com/feed/0b46f21fbe096b63df379948e1a0b943ebf8ac13.jpeg?token=98eac0d5f10e3043580add02ff8c06e7")
        mPathList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2F69%2F6a%2F45%2F696a45427403b660a5cbf8dfe6ba9567.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1657955420&t=939df2e364a92c67130fc13af5fbfa53")
        mPathList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.2qqtouxiang.com%2Fpic%2FBZ7495_05.jpg&refer=http%3A%2F%2Fimg.2qqtouxiang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1657955420&t=da780e39ee7f048291644be9d3d8eed5")
        initRecyclerView()
        mRvAdapter.onItemClick { i, s ->
            if (nowPosition == i){
                Toast.makeText(this,"跳转到点击到的详情页", Toast.LENGTH_SHORT).show()
            }else{
                nowPosition=i
                mRecyclerView.smoothScrollToPosition(i);
            }
        }
    }

    private fun initRecyclerView() {
        mLinearSnapHelper.attachToRecyclerView(mRecyclerView)
        mRecyclerView.addItemDecoration(GalleryItemDecoration())
        mRecyclerView.layoutManager = mCenterLayoutManager
        mRecyclerView.adapter = mRvAdapter
    }


}