package com.liyaan.myffmepgfirst.carhome

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.liyaan.myffmepgfirst.carhome.entity.SubjectEntity
import com.liyaan.myffmepgfirst.carhome.init.AppHelper
import com.liyaan.myffmepgfirst.carhome.sdb.AppDatabase
import com.liyaan.myffmepgfirst.exit.isConnectedNetWork

@OptIn(ExperimentalPagingApi::class)
class SubjectRemoteMediator (
    private val api:SubjectService,
    private val database: AppDatabase
) :RemoteMediator<Int,SubjectEntity>(){
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SubjectEntity>
    ): MediatorResult {
        try {
            Log.i("aaaa","$loadType")
            val pageKay = when(loadType){
                LoadType.REFRESH->null
                LoadType.PREPEND->return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND->{
                    val lastItem:SubjectEntity = state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
                    Log.i("aaaa","${lastItem.page}")
                    lastItem.page
                }
            }
            if (!AppHelper.mContext.isConnectedNetWork()){
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            Log.i("aaaa","$pageKay")
            val page = pageKay?:0
            val result = api.list(page,"294")
            val item = result.data.datas.map {
                SubjectEntity(id = it.id,
                    title = it.title,
                    link = it.link,
                    envelopePic = it.envelopePic,
                    projectLink = it.projectLink,
                    page = page+1
                )
            }
            var endOfPaginationReached = result!=null && result.errorCode==0 &&  result.data.datas.isEmpty()
            val subjectDao = database.subjectDao()
            database.withTransaction {
                if (loadType == LoadType.REFRESH){
                    subjectDao.clearSubject()
                }
                subjectDao.insertSubject(item)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e:Exception){
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }
}