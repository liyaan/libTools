package com.liyaan.myffmepgfirst.carhome.repository

import androidx.paging.*
import com.liyaan.myffmepgfirst.carhome.SubjectRemoteMediator
import com.liyaan.myffmepgfirst.carhome.SubjectService
import com.liyaan.myffmepgfirst.carhome.entity.SubjectEntity
import com.liyaan.myffmepgfirst.carhome.mapper.Mapper
import com.liyaan.myffmepgfirst.carhome.model.SubjectDataDataX
import com.liyaan.myffmepgfirst.carhome.sdb.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
@OptIn(ExperimentalPagingApi::class)
class SubjectRepositoryImpl(
    private val api:SubjectService,
    private val database: AppDatabase,
    private val mapperItemModel:Mapper<SubjectEntity,SubjectDataDataX>
):Repository {
    override fun fetchSubjectList(): Flow<PagingData<SubjectDataDataX>> {
         return Pager(
             config = PagingConfig(
             pageSize = 10,
             prefetchDistance = 1,
             initialLoadSize = 20
            ),
             remoteMediator = SubjectRemoteMediator(api,database) //负责网络请求，并将数据缓存到数据库
         ){
             database.subjectDao().subjectList() //从数据库中读取数据
         }.flow.flowOn(Dispatchers.IO).map { pagingData->
             pagingData.map {
                 mapperItemModel.map(it)  //对数据进行转换，给到UI显示
             }
         }
    }
}