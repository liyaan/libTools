package com.liyaan.myffmepgfirst.carhome.repository

import androidx.paging.PagingData
import com.liyaan.myffmepgfirst.carhome.model.SubjectDataDataX
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun fetchSubjectList():Flow<PagingData<SubjectDataDataX>>
}