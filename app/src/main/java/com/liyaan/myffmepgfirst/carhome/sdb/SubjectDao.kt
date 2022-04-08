package com.liyaan.myffmepgfirst.carhome.sdb

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.liyaan.myffmepgfirst.carhome.entity.SubjectEntity

@Dao
interface SubjectDao {
    @Query("DELETE FROM SubjectEntity")
    suspend fun clearSubject()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subjectList:List<SubjectEntity>)

    @Query("SELECT * FROM SubjectEntity")
    fun subjectList():PagingSource<Int,SubjectEntity>
 }